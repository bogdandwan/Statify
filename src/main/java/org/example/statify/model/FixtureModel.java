package org.example.statify.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.example.statify.api.score.ScoreResponseModel;
import org.example.statify.entity.DBFixture;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
public class FixtureModel {

    private Long id;
    private Integer apiId;
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
    private Integer homeTeamId;
    private Integer awayTeamId;
    private VenueModel venue;
    private ScoreResponseModel score;

    public FixtureModel(DBFixture dbFixture) {

        this.id = dbFixture.getId();
        this.apiId = dbFixture.getApiId();
        this.referee = dbFixture.getReferee();
        this.timezone = dbFixture.getTimezone();
        this.date = dbFixture.getDate();
        this.timestamp = dbFixture.getTimestamp();
        this.firstPeriod = dbFixture.getFirstPeriod();
        this.secondPeriod = dbFixture.getSecondPeriod();
        this.statusLong = dbFixture.getStatusLong();
        this.statusShort = dbFixture.getStatusShort();
        this.elapsed = dbFixture.getElapsed();
        this.extra = dbFixture.getExtra();

        if (dbFixture.getLeague() != null) {
            this.leagueId = dbFixture.getLeague().getApiId();
        }
        if (dbFixture.getSeason() != null) {
            this.seasonYear = dbFixture.getSeason().getYear();
        }
        if (dbFixture.getHomeTeam() != null) {
            this.homeTeamId = dbFixture.getHomeTeam().getApiId();
        }
        if (dbFixture.getAwayTeam() != null) {
            this.awayTeamId = dbFixture.getAwayTeam().getApiId();
        }
        this.venue = dbFixture.getVenue() == null ? null : new VenueModel(dbFixture.getVenue());
    }

}
