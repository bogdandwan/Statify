package org.example.statify.model;

import lombok.Getter;
import lombok.Setter;
import org.example.statify.dto.score.ScoreResponseModel;

import java.time.OffsetDateTime;

@Getter
@Setter
public class FixtureModel {

    private Long id;
    private String referee;
    private String timezone;
    private OffsetDateTime date;
    private Long timestamp;
    private Long firstPeriod;
    private Long secondPeriod;
    private String statusLong;
    private String statusShort;
    private Integer elapsed;
    private Integer extra;
    private Long leagueId;
    private Integer seasonYear;
    private Long homeTeamId;
    private Long awayTeamId;
    private Long venueId;
    private ScoreResponseModel score;
}
