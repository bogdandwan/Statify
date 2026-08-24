package org.example.statify.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.statify.client.impl.FootballApiClientImpl;
import org.example.statify.api.ApiResponseModel;
import org.example.statify.api.fixture.FixtureResponseModel;
import org.example.statify.api.FixtureSearch;
import org.example.statify.api.score.ScoreDetailResponseModel;
import org.example.statify.api.score.ScoreResponseModel;
import org.example.statify.entity.*;
import org.example.statify.entity.enums.ScoreType;
import org.example.statify.entity.exceptions.NotFoundException;
import org.example.statify.mapper.FixtureMapper;
import org.example.statify.mapper.ScoreMapper;
import org.example.statify.model.FixtureModel;
import org.example.statify.model.ScoreModel;
import org.example.statify.repository.*;
import org.example.statify.service.FixtureService;
import org.example.statify.service.TeamService;
import org.example.statify.service.VenueService;
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
    private final TeamRepository teamRepository;
    private final VenueService venueService;
    private final TeamService teamService;

    @Transactional
    @Override
    public void importFixtures(Long leagueId, Integer seasonYear) {
        log.info("Importing fixtures for league "+leagueId);

        final FixtureSearch search = new FixtureSearch(leagueId,seasonYear) ;
        ApiResponseModel<FixtureResponseModel> response = footballApiClient.getFixtures(search);

        for (FixtureResponseModel responseModel : response.getResponse()) {
            FixtureModel fixtureModel = fixtureMapper.toModel(responseModel);

            if (fixtureRepository.existsByApiId(fixtureModel.getId())) {
                continue;
            }
            DBFixture fixture = fixtureMapper.toEntity(fixtureModel);

            DBLeague league = leagueRepository.findByApiId(fixtureModel.getLeagueId());

            DBSeason season = seasonRepository.findByLeagueApiIdAndYear(fixtureModel.getLeagueId(), fixtureModel.getSeasonYear())
                    .orElseThrow(() ->
                            new NotFoundException("Season not found: " + fixtureModel.getSeasonYear()));

            DBTeam homeTeam = teamRepository.findByApiId(fixtureModel.getHomeTeamId());

            /*DBTeam awayTeam = teamRepository.findByApiId(fixtureModel.getAwayTeamId())
                    .orElseThrow(() -> new NotFoundException("Away team not found: " + fixtureModel.getAwayTeamId()));*/

            fixture.setAwayTeam(DBTeam.fromTeamIdOnly(teamService.getTeamByApiId(fixtureModel.getAwayTeamId())));

            fixture.setVenue(DBVenue.fromVenueIdOnly(venueService.getVenueByApiId(fixtureModel.getVenue().getApiId())));


            fixtureRepository.save(fixture);
            ScoreResponseModel score = fixtureModel.getScore();

            if (score != null) {

                saveScore(score.getHalftime(), ScoreType.HALFTIME, fixture);
                saveScore(score.getFulltime(), ScoreType.FULLTIME, fixture);
                saveScore(score.getExtratime(), ScoreType.EXTRATIME, fixture);
                saveScore(score.getPenalty(), ScoreType.PENALTY, fixture);
            }
        }
    }

    private void saveScore(ScoreDetailResponseModel scoreDetail, ScoreType type, DBFixture fixture) {

        if (scoreDetail.getHome() == null && scoreDetail.getAway() == null) {
            return;
        }
        ScoreModel scoreModel = scoreMapper.toModel(scoreDetail, type);
        DBScore score = scoreMapper.toEntity(scoreModel, fixture);
        scoreRepository.save(score);
    }
}
