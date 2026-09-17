package org.example.statify.service.impl;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.statify.api.ApiFixtureSearch;
import org.example.statify.api.ApiResponseModel;
import org.example.statify.api.fixture.FixtureResponseModel;
import org.example.statify.api.score.ScoreDetailResponseModel;
import org.example.statify.api.score.ScoreResponseModel;
import org.example.statify.client.impl.FootballApiClientImpl;
import org.example.statify.entity.*;
import org.example.statify.entity.enums.ScoreType;
import org.example.statify.entity.exceptions.NotFoundException;
import org.example.statify.entity.exceptions.ValidationException;
import org.example.statify.mapper.FixtureMapper;
import org.example.statify.mapper.ScoreMapper;
import org.example.statify.model.FixtureModel;
import org.example.statify.model.ScoreModel;
import org.example.statify.repository.*;
import org.example.statify.search.FixtureSearch;
import org.example.statify.search.spec.FixtureSpec;
import org.example.statify.service.FixtureService;
import org.example.statify.service.TeamService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FixtureServiceImpl implements FixtureService {

  private final FootballApiClientImpl footballApiClient;

  private final FixtureMapper fixtureMapper;
  private final ScoreMapper scoreMapper;
  private final TeamService teamService;

  private final FixtureRepository fixtureRepository;
  private final ScoreRepository scoreRepository;

  private final LeagueRepository leagueRepository;
  private final SeasonRepository seasonRepository;
  private final VenueRepository venueRepository;
  private final TeamRepository teamRepository;

  @Value("${scheduler.zone}")
  private String schedulerZone;

  @Transactional
  @Override
  public void importFixtures(Integer leagueId, Integer seasonYear) {

    log.info("Importing fixtures for league {} and season {}", leagueId, seasonYear);

    final ApiFixtureSearch search =
        new ApiFixtureSearch().setLeague(leagueId).setSeason(seasonYear);

    final ApiResponseModel<FixtureResponseModel> response = footballApiClient.getFixtures(search);

    log.info("TOTAL FIXTURES FROM API = {}", response.getResponse().size());

    for (FixtureResponseModel responseModel : response.getResponse()) {

      FixtureModel fixtureModel = fixtureMapper.toModel(responseModel);

      log.info(
          "Fixture API ID = {} | league = {} | season = {}",
          fixtureModel.getApiId(),
          fixtureModel.getLeagueId(),
          fixtureModel.getSeasonYear());

      saveFromApiFixture(responseModel);
    }
  }

  @Override
  public void importAllFixtures(Integer leagueId, Integer seasonYear) {

    log.info(
        "Starting FT fixtures import | league filter = {} | season filter = {}",
        leagueId,
        seasonYear);

    List<DBLeague> leagues = leagueRepository.findAll();

    for (DBLeague league : leagues) {

      if (league.getApiId() == null) {
        log.warn("League with database ID {} has no apiId. Skipping.", league.getId());
        continue;
      }

      if (leagueId != null && !leagueId.equals(league.getApiId())) {
        continue;
      }

      for (DBSeason season : league.getSeasons()) {

        if (season.getYear() == null) {
          continue;
        }

        if (seasonYear != null && !seasonYear.equals(season.getYear())) {
          continue;
        }

        Integer currentLeagueId = league.getApiId();
        Integer currentSeasonYear = season.getYear();

        log.info(
            "Importing FT fixtures | league = {} | season = {}",
            currentLeagueId,
            currentSeasonYear);

        ApiFixtureSearch search =
            new ApiFixtureSearch()
                .setLeague(currentLeagueId)
                .setSeason(currentSeasonYear)
                .setStatus("FT");

        ApiResponseModel<FixtureResponseModel> response;

        try {

          response = footballApiClient.getFixtures(search);

        } catch (Exception e) {

          log.error(
              "FAILED to get fixtures after retries | league = {} | season = {} | error = {}",
              currentLeagueId,
              currentSeasonYear,
              e.getMessage(),
              e);

          continue;
        }

        if (response == null || response.getResponse() == null) {

          log.warn(
              "No FT fixtures returned | league = {} | season = {}",
              currentLeagueId,
              currentSeasonYear);

          continue;
        }

        log.info(
            "FT FIXTURES FROM API = {} | league = {} | season = {}",
            response.getResponse().size(),
            currentLeagueId,
            currentSeasonYear);

        for (FixtureResponseModel responseModel : response.getResponse()) {

          try {

            saveFromApiFixture(responseModel);

          } catch (Exception e) {

            Integer fixtureApiId =
                responseModel.getFixture() != null ? responseModel.getFixture().getId() : null;

            log.error(
                "FAILED to save fixture | fixture = {} | league = {} | season = {} | error = {}",
                fixtureApiId,
                currentLeagueId,
                currentSeasonYear,
                e.getMessage(),
                e);
          }
        }

        log.info(
            "Finished league-season | league = {} | season = {}",
            currentLeagueId,
            currentSeasonYear);
      }
    }

    log.info("Finished FT fixtures import");
  }

  private DBTeam getOrImportTeam(Integer teamApiId) {

    DBTeam team = teamRepository.findByApiId(teamApiId);

    if (team != null) {
      return team;
    }

    log.warn("Team with API ID {} not found in database. Importing from API...", teamApiId);

    teamService.saveTeamById(teamApiId);

    team = teamRepository.findByApiId(teamApiId);

    if (team == null) {
      throw new NotFoundException("Team could not be imported: " + teamApiId);
    }

    log.info("Team with API ID {} successfully imported", teamApiId);

    return team;
  }

  public FixtureModel saveFromApiFixture(FixtureResponseModel responseModel) {

    FixtureModel fixtureModel = fixtureMapper.toModel(responseModel);

    if (fixtureRepository.existsByApiId(fixtureModel.getApiId())) {

      log.info("Fixture with API ID {} already exists. Skipping import.", fixtureModel.getApiId());

      return null;
    }

    DBFixture fixture = fixtureMapper.toEntity(fixtureModel);

    // LEAGUE
    DBLeague league = leagueRepository.findByApiId(fixtureModel.getLeagueId());

    if (league == null) {
      throw new NotFoundException("League not found: " + fixtureModel.getLeagueId());
    }

    fixture.setLeague(league);

    // SEASON
    DBSeason season =
        seasonRepository.findByLeagueApiIdAndYear(
            fixtureModel.getLeagueId(), fixtureModel.getSeasonYear());

    if (season == null) {
      throw new NotFoundException(
          "Season not found: league = "
              + fixtureModel.getLeagueId()
              + ", season = "
              + fixtureModel.getSeasonYear());
    }

    fixture.setSeason(season);

    DBTeam homeTeam = getOrImportTeam(fixtureModel.getHomeTeamId());

    fixture.setHomeTeam(homeTeam);

    DBTeam awayTeam = getOrImportTeam(fixtureModel.getAwayTeamId());

    fixture.setAwayTeam(awayTeam);

    if (fixtureModel.getVenue() != null && fixtureModel.getVenue().getApiId() != null) {

      DBVenue venue = venueRepository.findByApiId(fixtureModel.getVenue().getApiId());

      if (venue != null) {

        fixture.setVenue(venue);

      } else {

        log.warn(
            "Venue with API ID {} not found. Fixture {} will be saved without venue.",
            fixtureModel.getVenue().getApiId(),
            fixtureModel.getApiId());
      }
    }

    fixtureRepository.save(fixture);

    log.info(
        "Fixture with API ID {} successfully imported | league = {} | season = {}",
        fixtureModel.getApiId(),
        fixtureModel.getLeagueId(),
        fixtureModel.getSeasonYear());

    return fixtureModel;
  }

  public FixtureModel saveFixtureById(Integer fixtureId) {

    final ApiFixtureSearch search = new ApiFixtureSearch().setId(fixtureId);
    final ApiResponseModel<FixtureResponseModel> response = footballApiClient.getFixtures(search);

    if (response == null || response.getResponse() == null || response.getResponse().size() != 1) {
      throw new ValidationException("Fixture by id: " + fixtureId + " not found");
    }
    return saveFromApiFixture(response.getResponse().get(0));
  }

  private void saveScores(ScoreResponseModel score, DBFixture fixture) {

    if (score == null) {
      return;
    }
    saveSecondHalfScore(score.getHalftime(), score.getFulltime(), fixture);

    saveScore(score.getHalftime(), ScoreType.FIRST_HALF, fixture);
    saveScore(score.getFulltime(), ScoreType.FULLTIME, fixture);
    saveScore(score.getExtratime(), ScoreType.EXTRATIME, fixture);
    saveScore(score.getPenalty(), ScoreType.PENALTY, fixture);
  }

  private void saveScore(ScoreDetailResponseModel scoreDetail, ScoreType type, DBFixture fixture) {

    if (scoreDetail == null) {
      return;
    }
    if (scoreDetail.getHome() == null && scoreDetail.getAway() == null) {
      return;
    }
    ScoreModel scoreModel = scoreMapper.toModel(scoreDetail, type);
    DBScore score = scoreMapper.toEntity(scoreModel, fixture);

    scoreRepository.save(score);
  }

  private void saveSecondHalfScore(
      ScoreDetailResponseModel halftime, ScoreDetailResponseModel fulltime, DBFixture fixture) {

    if (halftime == null || fulltime == null) {
      return;
    }

    if (halftime.getHome() == null
        || halftime.getAway() == null
        || fulltime.getHome() == null
        || fulltime.getAway() == null) {
      return;
    }

    ScoreModel scoreModel = scoreMapper.toSecondHalfModel(halftime, fulltime);

    DBScore score = scoreMapper.toEntity(scoreModel, fixture);

    scoreRepository.save(score);
  }

  @Override
  public List<FixtureModel> findAll(FixtureSearch search) {
    final List<DBFixture> dbFixtures = fixtureRepository.findAll(new FixtureSpec(search));

    return dbFixtures.stream().map(FixtureModel::new).collect(Collectors.toList());
  }

  @Override
  public FixtureModel findById(Long id) {
    if (id == null) {
      throw new NotFoundException("Fixture id cannot be null");
    }
    DBFixture dbFixture =
        fixtureRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Fixture not found by id: " + id));

    return new FixtureModel(dbFixture);
  }

  @Override
  public FixtureModel findByApiId(Integer apiId) {

    if (apiId == null) {
      throw new NotFoundException("Fixture not found.");
    } else {
      DBFixture dbFixture = fixtureRepository.findByApiId(apiId);
      return new FixtureModel(dbFixture);
    }
  }

  @Transactional
  @Override
  public void syncUpcomingFixtures() {

    LocalDate today = LocalDate.now();

    ApiFixtureSearch search =
        new ApiFixtureSearch()
            .setLeague(39)
            .setSeason(2026)
            .setFrom(today.toString())
            .setTo(today.plusDays(30).toString());

    ApiResponseModel<FixtureResponseModel> response = footballApiClient.getFixtures(search);

    for (FixtureResponseModel fixtureResponse : response.getResponse()) {

      System.out.println(
          fixtureResponse.getFixture().getId()
              + " | "
              + fixtureResponse.getFixture().getDate()
              + " | "
              + fixtureResponse.getFixture().getStatus());

      saveOrUpdateFixture(fixtureResponse);
    }
  }

  private void saveOrUpdateFixture(FixtureResponseModel fixtureResponse) {

    Integer apiId = fixtureResponse.getFixture().getId();

    DBFixture fixture = fixtureRepository.findByApiId(apiId);

    if (fixture == null) {

      log.info("Fixture API ID {} does not exist. Creating new fixture.", apiId);

      fixture = new DBFixture();
      fixture.setApiId(apiId);

    } else {

      log.info("Fixture API ID {} already exists. Updating fixture.", apiId);
    }

    fixture.setReferee(fixtureResponse.getFixture().getReferee());
    fixture.setTimezone(fixtureResponse.getFixture().getTimezone());
    fixture.setDate(OffsetDateTime.parse(fixtureResponse.getFixture().getDate()));
    fixture.setTimestamp(fixtureResponse.getFixture().getTimestamp());

    if (fixtureResponse.getFixture().getPeriods() != null) {

      fixture.setFirstPeriod(fixtureResponse.getFixture().getPeriods().getFirst());

      fixture.setSecondPeriod(fixtureResponse.getFixture().getPeriods().getSecond());
    }

    if (fixtureResponse.getFixture().getStatus() != null) {

      fixture.setStatusLong(fixtureResponse.getFixture().getStatus().getLongName());

      fixture.setStatusShort(fixtureResponse.getFixture().getStatus().getShortName());

      fixture.setElapsed(fixtureResponse.getFixture().getStatus().getElapsed());

      fixture.setExtra(fixtureResponse.getFixture().getStatus().getExtra());
    }

    DBLeague league = leagueRepository.findByApiId(fixtureResponse.getLeague().getId());

    if (league == null) {
      throw new IllegalStateException(
          "League not found for apiId: " + fixtureResponse.getLeague().getId());
    }

    fixture.setLeague(league);

    DBSeason season =
        seasonRepository.findByLeague_IdAndYear(
            league.getId(), fixtureResponse.getLeague().getSeason());

    if (season == null) {
      throw new IllegalStateException(
          "Season not found. League: "
              + league.getName()
              + ", year: "
              + fixtureResponse.getLeague().getSeason());
    }

    fixture.setSeason(season);

    DBTeam homeTeam =
        teamRepository.findByApiId(fixtureResponse.getTeams().getHome().getId().intValue());

    if (homeTeam == null) {
      throw new IllegalStateException(
          "Home team not found for apiId: " + fixtureResponse.getTeams().getHome().getId());
    }

    fixture.setHomeTeam(homeTeam);

    DBTeam awayTeam =
        teamRepository.findByApiId(fixtureResponse.getTeams().getAway().getId().intValue());

    if (awayTeam == null) {
      throw new IllegalStateException(
          "Away team not found for apiId: " + fixtureResponse.getTeams().getAway().getId());
    }

    fixture.setAwayTeam(awayTeam);

    if (fixtureResponse.getFixture().getVenue() != null
        && fixtureResponse.getFixture().getVenue().getId() != null) {

      DBVenue venue =
          venueRepository.findByApiId(fixtureResponse.getFixture().getVenue().getId().intValue());

      if (venue == null) {
        throw new IllegalStateException(
            "Venue not found for apiId: " + fixtureResponse.getFixture().getVenue().getId());
      }

      fixture.setVenue(venue);
    }

    fixtureRepository.saveAndFlush(fixture);

    log.info("Fixture API ID {} successfully saved/updated.", apiId);
  }
}
