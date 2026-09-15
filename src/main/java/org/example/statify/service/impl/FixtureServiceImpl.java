package org.example.statify.service.impl;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FixtureServiceImpl implements FixtureService {

  private final FootballApiClientImpl footballApiClient;

  private final FixtureMapper fixtureMapper;
  private final ScoreMapper scoreMapper;

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
        "Starting fixtures import | league filter = {} | season filter = {}", leagueId, seasonYear);

    List<DBLeague> leagues = leagueRepository.findAll();

    for (DBLeague league : leagues) {

      if (leagueId != null && !leagueId.equals(league.getApiId())) {
        continue;
      }

      if (league.getApiId() == null) {
        log.warn("League with database ID {} has no apiId. Skipping.", league.getId());
        continue;
      }

      for (DBSeason season : league.getSeasons()) {

        if (seasonYear != null && !seasonYear.equals(season.getYear())) {
          continue;
        }

        if (season.getYear() == null) {
          continue;
        }

        log.info(
            "Importing fixtures | league = {} | season = {}", league.getApiId(), season.getYear());

        importFixtures(league.getApiId(), season.getYear());
      }
    }

    log.info("Finished fixtures import");
  }

  public FixtureModel saveFromApiFixture(FixtureResponseModel responseModel) {

    FixtureModel fixtureModel = fixtureMapper.toModel(responseModel);

    if (fixtureRepository.existsByApiId(fixtureModel.getApiId())) {

      log.info("Fixture with API ID {} already exists. Skipping import.", fixtureModel.getApiId());

      return null;
    }

    DBFixture fixture = fixtureMapper.toEntity(fixtureModel);

    DBLeague league = leagueRepository.findByApiId(fixtureModel.getLeagueId());

    if (league == null) {
      throw new NotFoundException("League not found: " + fixtureModel.getLeagueId());
    }

    fixture.setLeague(league);

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

    DBTeam homeTeam = teamRepository.findByApiId(fixtureModel.getHomeTeamId());

    if (homeTeam == null) {
      throw new NotFoundException("Home team not found: " + fixtureModel.getHomeTeamId());
    }

    fixture.setHomeTeam(homeTeam);

    DBTeam awayTeam = teamRepository.findByApiId(fixtureModel.getAwayTeamId());

    if (awayTeam == null) {
      throw new NotFoundException("Away team not found: " + fixtureModel.getAwayTeamId());
    }

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

  @Override
  public void calculateGgForDate(LocalDate date) {

    ZoneId zoneId = ZoneId.of(schedulerZone);

    OffsetDateTime from = date.atStartOfDay(zoneId).toOffsetDateTime();

    OffsetDateTime to = date.plusDays(1).atStartOfDay(zoneId).toOffsetDateTime();

    List<DBFixture> fixtures =
        fixtureRepository.findByDateGreaterThanEqualAndDateLessThanAndStatusShortIn(
            from, to, Set.of("FT", "AET", "PEN"));

    System.out.println("GG CALCULATION FOR " + date + " | FIXTURES FOUND: " + fixtures.size());

    for (DBFixture fixture : fixtures) {

      calculateGg(fixture);
    }
  }

  private void calculateGg(DBFixture fixture) {

    DBScore firstHalf =
        fixture.getScores().stream()
            .filter(score -> score.getType() == ScoreType.FIRST_HALF)
            .findFirst()
            .orElse(null);

    DBScore secondHalf =
        fixture.getScores().stream()
            .filter(score -> score.getType() == ScoreType.SECOND_HALF)
            .findFirst()
            .orElse(null);

    if (firstHalf == null || secondHalf == null) {

      System.out.println("GG SKIPPED - SCORES MISSING | fixtureApiId=" + fixture.getApiId());

      return;
    }

    if (firstHalf.getHome() == null
        || firstHalf.getAway() == null
        || secondHalf.getHome() == null
        || secondHalf.getAway() == null) {

      System.out.println("GG SKIPPED - SCORE VALUES NULL | fixtureApiId=" + fixture.getApiId());

      return;
    }

    boolean firstHalfGg = firstHalf.getHome() > 0 && firstHalf.getAway() > 0;

    boolean secondHalfGg = secondHalf.getHome() > 0 && secondHalf.getAway() > 0;

    fixture.setFirstHalfGg(firstHalfGg);
    fixture.setSecondHalfGg(secondHalfGg);

    fixtureRepository.save(fixture);

    System.out.println(
        "fixtureApiId="
            + fixture.getApiId()
            + " | FIRST HALF="
            + firstHalf.getHome()
            + ":"
            + firstHalf.getAway()
            + " | 1GG="
            + firstHalfGg
            + " | SECOND HALF="
            + secondHalf.getHome()
            + ":"
            + secondHalf.getAway()
            + " | 2GG="
            + secondHalfGg);
  }
}
