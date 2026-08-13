package org.example.statify.dto.fixtures;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FixtureStatusModel {

    @JsonProperty("long")
    private String longStatus;

    @JsonProperty("short")
    private String shortStatus;

    private Integer elapsed;
    private Integer extra;
}
