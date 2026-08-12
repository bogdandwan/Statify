package org.example.statify.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LeagueModel {

    private Long id;
    private String name;
    private String type;
    private String logo;
    private CountryModel country;
    private List<SeasonModel> seasons;
}
