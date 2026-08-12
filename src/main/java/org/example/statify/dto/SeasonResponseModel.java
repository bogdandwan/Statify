package org.example.statify.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeasonResponseModel {

    private Integer year;
    private String start;
    private String end;
    private Boolean current;
    private CoverageResponseModel coverage;
}
