package org.example.statify.dto.fixtures;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FixtureResponseModel {

    private FixtureInfoModel fixture;
    private FixtureLeagueModel league;
    private FixtureTeamsModel teams;
    private FixtureGoalsModel goals;
    private FixtureScoreModel score;
}
