package org.example.statify.service.impl;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.statify.api.ApiResponseModel;
import org.example.statify.api.FixtureSearch;
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
import org.example.statify.search.spec.FixtureSpec;
import org.example.statify.service.FixtureService;
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

  @Transactional
  @Override
  public void importFixtures(Integer leagueId, Integer seasonYear) {

    log.info("Importing fixtures for league {} and season {}", leagueId, seasonYear);

    final FixtureSearch search = new FixtureSearch().setLeague(leagueId).setSeason(seasonYear);
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

  public FixtureModel saveFromApiFixture(FixtureResponseModel responseModel) {

    FixtureModel fixtureModel = fixtureMapper.toModel(responseModel);

    if (fixtureRepository.existsByApiId(fixtureModel.getApiId())) {
      return null;
    }
    DBFixture fixture = fixtureMapper.toEntity(fixtureModel);
    DBLeague league = leagueRepository.findByApiId(fixtureModel.getLeagueId());

    if (league == null) {
      throw new NotFoundException("League not found: " + fixtureModel.getLeagueId());
    }
    DBSeason season =
        seasonRepository.findByLeagueApiIdAndYear(
            fixtureModel.getLeagueId(), fixtureModel.getSeasonYear());

    fixture.setLeague(league);
    fixture.setSeason(season);
    /*fixture.setHomeTeam(DBTeam.fromTeamIdOnly(teamService.getTeamByApiId(fixtureModel.getHomeTeamId())));
    fixture.setAwayTeam(DBTeam.fromTeamIdOnly(teamService.getTeamByApiId(fixtureModel.getAwayTeamId())));*/

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

    /***
     * 429 Too Many Requests from GET
     **/
    /*if (fixtureModel.getVenue() != null && fixtureModel.getVenue().getApiId() != null) {
        fixture.setVenue(DBVenue.fromVenueIdOnly(venueService.getVenueByApiId(fixtureModel.getVenue().getApiId())));
    }*/

    if (fixtureModel.getVenue() != null && fixtureModel.getVenue().getApiId() != null) {
      DBVenue venue = venueRepository.findByApiId(fixtureModel.getVenue().getApiId());

      if (venue != null) {
        fixture.setVenue(venue);
      } else {
        log.warn("Venue with API id {} not found", fixtureModel.getVenue().getApiId());
      }
    }

    DBFixture savedFixture = fixtureRepository.save(fixture);
    saveScores(fixtureModel.getScore(), savedFixture);
    return new FixtureModel(savedFixture);
  }

  public FixtureModel saveFixtureById(Integer fixtureId) {

    final FixtureSearch search = new FixtureSearch().setId(fixtureId);
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
  public List<DBFixture> findAll(org.example.statify.search.FixtureSearch search) {
    FixtureSpec spec = new FixtureSpec(search);

    return fixtureRepository.findAll(spec);
  }

  @Override
  public DBFixture findById(Long id) {
    return fixtureRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Fixture not found."));
  }

  @Override
  public DBFixture findByApiId(Integer apiId) {

    if (apiId == null) {
      throw new NotFoundException("Fixture not found.");
    } else {
      return fixtureRepository.findByApiId(apiId);
    }
  }

  @Transactional
  @Override
  public void syncUpcomingFixtures() {

    LocalDate today = LocalDate.now();

    FixtureSearch search =
        new FixtureSearch()
            .setLeague(39)
            .setSeason(2026)
            .setFrom(today.toString())
            .setTo(today.plusDays(7).toString());

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
      fixture = new DBFixture();
      fixture.setApiId(apiId);
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

    System.out.println("SAVING FIXTURE: " + fixture.getApiId());

    fixtureRepository.saveAndFlush(fixture);

    System.out.println("FIXTURE SAVED: " + fixture.getApiId());
  }
}
