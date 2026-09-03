package org.example.statify.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fixtures")
@Getter
@Setter
public class DBFixture {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "api_id", nullable = false, unique = true)
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "league_id")
  private DBLeague league;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "season_id")
  private DBSeason season;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "home_team_id")
  private DBTeam homeTeam;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "away_team_id")
  private DBTeam awayTeam;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "venue_id")
  private DBVenue venue;

  @OneToMany(mappedBy = "fixture")
  private List<DBScore> scores;
}
