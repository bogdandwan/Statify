package org.example.statify.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "fixtures")
@Getter
@Setter
public class DBFixture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "api_id", unique = true, nullable = false)
    private Long apiId;

    @Column(name = "referee")
    private String referee;

    @Column(name = "date")
    private OffsetDateTime date;

    @Column(name = "status_long")
    private String statusLong;

    @Column(name = "status_short")
    private String statusShort;

    @Column(name = "elapsed")
    private Integer elapsed;

    @Column(name = "extra")
    private Integer extra;

    @Column(name = "home_goals")
    private Integer homeGoals;

    @Column(name = "away_goals")
    private Integer awayGoals;

    @Column(name = "halftime_home")
    private Integer halftimeHome;

    @Column(name = "halftime_away")
    private Integer halftimeAway;

    @Column(name = "fulltime_home")
    private Integer fulltimeHome;

    @Column(name = "fulltime_away")
    private Integer fulltimeAway;

    @Column(name = "extratime_home")
    private Integer extratimeHome;

    @Column(name = "extratime_away")
    private Integer extratimeAway;

    @Column(name = "penalty_home")
    private Integer penaltyHome;

    @Column(name = "penalty_away")
    private Integer penaltyAway;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id", nullable = false)
    private DBSeason season;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id")
    private DBVenue venue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_team_id", nullable = false)
    private DBTeam homeTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "away_team_id", nullable = false)
    private DBTeam awayTeam;
}
