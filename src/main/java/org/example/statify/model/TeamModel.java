package org.example.statify.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamModel {

    private Long id;
    private Long apiId;
    private String name;
    private String code;
    private String country;
    private Integer founded;
    private Boolean national;
    private String logo;
}
