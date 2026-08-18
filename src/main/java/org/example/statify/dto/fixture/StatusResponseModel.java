package org.example.statify.dto.fixture;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusResponseModel {

    @JsonProperty("long")
    private String longName;

    @JsonProperty("short")
    private String shortName;

    private Integer elapsed;
    private Integer extra;
}
