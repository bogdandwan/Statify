package org.example.statify.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.statify.client.FootballApiClient;
import org.example.statify.dto.DTOResponseModel;
import org.example.statify.dto.fixtures.FixtureResponseModel;
import org.example.statify.entity.DBFixture;
import org.example.statify.entity.DBSeason;
import org.example.statify.entity.DBTeam;
import org.example.statify.mapper.FixtureMapper;
import org.example.statify.model.FixtureModel;
import org.example.statify.repository.FixtureRepository;
import org.example.statify.repository.SeasonRepository;
import org.example.statify.repository.TeamRepository;
import org.example.statify.service.FixtureService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FixtureServiceImpl implements FixtureService {

    private final FootballApiClient footballApiClient;
    private final FixtureMapper fixtureMapper;

    private final FixtureRepository fixtureRepository;
    private final SeasonRepository seasonRepository;
    private final TeamRepository teamRepository;


    @Transactional
    @Override
    public void importFixtures(Long leagueId, Integer seasonYear) {
        DTOResponseModel<FixtureResponseModel> response =
                footballApiClient.getFixtures(leagueId, seasonYear);


        for (FixtureResponseModel responseModel : response.getResponse()) {

            // 1. ResponseModel -> FixtureModel
            FixtureModel fixtureModel = fixtureMapper.toModel(responseModel);

            // 2. FixtureModel -> DBFixture
            DBFixture fixture = fixtureMapper.toEntity(fixtureModel);

            // 3. Pronađi Season
            DBSeason season = seasonRepository.findByLeague_ApiIdAndYear(leagueId, seasonYear)
                            .orElseThrow(() ->
                                    new IllegalStateException(
                                            "Season not found for league: " + leagueId + ", year: " + seasonYear));

            // 4. Pronađi Home Team
            DBTeam homeTeam = teamRepository.findByApiId(fixtureModel.getHomeTeamId())
                            .orElseThrow(() ->
                                    new IllegalStateException("Home team not found: " + fixtureModel.getHomeTeamId()));

            // 5. Pronađi Away Team
            DBTeam awayTeam = teamRepository.findByApiId(fixtureModel.getAwayTeamId())
                            .orElseThrow(() ->
                                    new IllegalStateException("Away team not found: " + fixtureModel.getAwayTeamId()));


            // 6. Postavi veze
            fixture.setSeason(season);
            fixture.setHomeTeam(homeTeam);
            fixture.setAwayTeam(awayTeam);


            fixtureRepository.save(fixture);
        }

    }
}
