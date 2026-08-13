package org.example.statify.dto.fixtures;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FixtureScoreModel {

    private FixtureScorePartModel halftime;
    private FixtureScorePartModel fulltime;
    private FixtureScorePartModel extratime;
    private FixtureScorePartModel penalty;
}
