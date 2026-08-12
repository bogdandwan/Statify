package org.example.statify.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FixturesResponseModel {

    private Boolean events;
    private Boolean lineups;
    private Boolean statistics_fixtures;
    private Boolean statistics_players;
}
