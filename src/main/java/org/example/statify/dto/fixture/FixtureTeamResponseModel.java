package org.example.statify.dto.fixture;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FixtureTeamResponseModel {

    private Long id;
    private String name;
    private String logo;
    private Boolean winner;
}
