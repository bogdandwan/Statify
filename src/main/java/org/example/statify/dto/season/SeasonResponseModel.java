package org.example.statify.dto.season;

import lombok.Getter;
import lombok.Setter;
import org.example.statify.dto.coverage.CoverageResponseModel;

@Getter
@Setter
public class SeasonResponseModel {

    private Integer year;
    private String start;
    private String end;
    private Boolean current;
    private CoverageResponseModel coverage;
}
