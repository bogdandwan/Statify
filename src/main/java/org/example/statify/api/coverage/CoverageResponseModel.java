package org.example.statify.api.coverage;

import lombok.Getter;
import lombok.Setter;
import org.example.statify.api.fixture.FixtureResponseModel;

@Getter
@Setter
public class CoverageResponseModel {

    private FixtureResponseModel fixtures;
    private Boolean standings;
    private Boolean players;
    private Boolean top_scorers;
    private Boolean top_assists;
    private Boolean top_cards;
    private Boolean injuries;
    private Boolean predictions;
    private Boolean odds;
}
