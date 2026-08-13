package org.example.statify.model;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class FixtureModel {

    private Long apiId;
    private String referee;
    private OffsetDateTime date;
    private String statusLong;
    private String statusShort;
    private Integer elapsed;
    private Integer extra;
    private Integer homeGoals;
    private Integer awayGoals;
    private Integer halftimeHome;
    private Integer halftimeAway;
    private Integer fulltimeHome;
    private Integer fulltimeAway;
    private Integer extratimeHome;
    private Integer extratimeAway;
    private Integer penaltyHome;
    private Integer penaltyAway;
    private Integer season;
    private Long leagueId;
    private Long homeTeamId;
    private Long awayTeamId;
}
