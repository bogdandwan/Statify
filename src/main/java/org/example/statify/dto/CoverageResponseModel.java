package org.example.statify.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoverageResponseModel {

    private FixturesResponseModel fixtures;
    private Boolean standings;
    private Boolean players;
    private Boolean top_scorers;
    private Boolean top_assists;
    private Boolean top_cards;
    private Boolean injuries;
    private Boolean predictions;
    private Boolean odds;
}
