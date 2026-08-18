package org.example.statify.dto.fixture;

import lombok.Getter;
import lombok.Setter;

import org.example.statify.dto.score.ScoreResponseModel;
import org.example.statify.dto.fixture.TeamResponseModel;

@Getter
@Setter
public class FixtureResponseModel {

    private FixtureDetailResponseModel fixture;
    private FixtureLeagueResponseModel league;
    private TeamResponseModel teams;
    private ScoreResponseModel score;

}
