package org.example.statify.dto.fixture;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FixtureLeagueResponseModel {

    private Long id;
    private String name;
    private String country;
    private String logo;
    private String flag;
    private Integer season;
    private String round;
    private Boolean standings;
}
