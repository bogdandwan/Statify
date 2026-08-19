package org.example.statify.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.statify.client.FootballApiClient;
import org.example.statify.dto.DTOResponseModel;
import org.example.statify.dto.fixture.FixtureResponseModel;
import org.example.statify.dto.score.ScoreDetailResponseModel;
import org.example.statify.dto.score.ScoreResponseModel;
import org.example.statify.entity.*;
import org.example.statify.entity.enums.ScoreType;
import org.example.statify.entity.exceptions.NotFoundException;
import org.example.statify.mapper.FixtureMapper;
import org.example.statify.mapper.ScoreMapper;
import org.example.statify.model.FixtureModel;
import org.example.statify.model.ScoreModel;
import org.example.statify.repository.*;
import org.example.statify.service.FixtureService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FixtureServiceImpl implements FixtureService {

    private final FootballApiClient footballApiClient;

    private final FixtureMapper fixtureMapper;
    private final ScoreMapper scoreMapper;

    private final FixtureRepository fixtureRepository;
    private final ScoreRepository scoreRepository;

    private final LeagueRepository leagueRepository;
    private final SeasonRepository seasonRepository;
    private final TeamRepository teamRepository;
    private final VenueRepository venueRepository;

    @Transactional
    @Override
    public void importFixtures(Long leagueId, Integer seasonYear) {

        DTOResponseModel<FixtureResponseModel> response = footballApiClient.getFixtures(leagueId, seasonYear);

        for (FixtureResponseModel responseModel : response.getResponse()) {
            FixtureModel fixtureModel = fixtureMapper.toModel(responseModel);

            if (fixtureRepository.existsByApiId(fixtureModel.getId())) {
                continue;
            }

            DBLeague league = leagueRepository.findByApiId(fixtureModel.getLeagueId())
                            .orElseThrow(() ->
                                    new NotFoundException("League not found: " + fixtureModel.getLeagueId()));

            DBSeason season = seasonRepository.findByLeagueApiIdAndYear(fixtureModel.getLeagueId(), fixtureModel.getSeasonYear())
                    .orElseThrow(() ->
                            new NotFoundException("Season not found: " + fixtureModel.getSeasonYear()));

            DBTeam homeTeam = teamRepository.findByApiId(fixtureModel.getHomeTeamId())
                    .orElseThrow(() ->
                            new NotFoundException("Home team not found: " + fixtureModel.getHomeTeamId()));

            DBTeam awayTeam = teamRepository.findByApiId(fixtureModel.getAwayTeamId())
                    .orElseThrow(() -> new NotFoundException("Away team not found: " + fixtureModel.getAwayTeamId()));

            DBVenue venue = venueRepository.findByApiId(fixtureModel.getVenueId())
                    .orElseThrow(() -> new NotFoundException("Venue not found: " + fixtureModel.getVenueId()));

            DBFixture fixture = fixtureMapper.toEntity(fixtureModel, league, season, homeTeam, awayTeam, venue);
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
