package org.example.statify.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.statify.dto.score.ScoreResponseModel;
import org.example.statify.entity.DBFixture;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
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
    private Integer leagueId;
    private Integer seasonYear;
    private Long homeTeamId;
    private Long awayTeamId;
    private VenueModel venue;
    private ScoreResponseModel score;

    public FixtureModel(DBFixture dbFixture) {
        this.id = dbFixture.getId();
        this.venue = dbFixture.getVenue() == null ? null : new VenueModel(dbFixture.getVenue());
    }
}
