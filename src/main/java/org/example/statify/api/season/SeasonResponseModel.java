package org.example.statify.api.season;

import lombok.Getter;
import lombok.Setter;
import org.example.statify.api.coverage.CoverageResponseModel;

@Getter
@Setter
public class SeasonResponseModel {

    private Integer year;
    private String start;
    private String end;
    private Boolean current;
    private CoverageResponseModel coverage;
}
