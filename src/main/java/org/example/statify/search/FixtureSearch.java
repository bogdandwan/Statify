package org.example.statify.search;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.example.statify.entity.enums.ScoreType;

import java.time.OffsetDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class FixtureSearch {

    private String statusShort;
    private Long leagueId;
    private Integer seasonYear;
    private Long teamId;
    private OffsetDateTime dateFrom;
    private OffsetDateTime dateTo;
    private ScoreType scoreType;
}
