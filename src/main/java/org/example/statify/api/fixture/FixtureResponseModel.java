package org.example.statify.api.fixture;

import lombok.Getter;
import lombok.Setter;

import org.example.statify.api.score.ScoreResponseModel;

@Getter
@Setter
public class FixtureResponseModel {

    private FixtureDetailResponseModel fixture;
    private FixtureLeagueResponseModel league;
    private TeamResponseModel teams;
    private ScoreResponseModel score;

}
