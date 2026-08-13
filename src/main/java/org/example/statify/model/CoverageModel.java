package org.example.statify.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoverageModel {

    private Boolean standings;
    private Boolean players;
    private Boolean topScorers;
    private Boolean topAssists;
    private Boolean topCards;
    private Boolean injuries;
    private Boolean predictions;
    private Boolean odds;
    private FixtureModel fixture;

}
