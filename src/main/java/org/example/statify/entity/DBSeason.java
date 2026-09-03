package org.example.statify.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "seasons")
public class DBSeason {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "year", nullable = false)
  private Integer year;

  @Column(name = "start", nullable = false)
  private LocalDate start;

  @Column(name = "end", nullable = false)
  private LocalDate end;

  @Column(name = "current", nullable = false)
  private Boolean current;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "league_id", nullable = false)
  private DBLeague league;

  @OneToOne(mappedBy = "season", cascade = CascadeType.ALL)
  private DBCoverage coverage;

  /*@OneToMany(mappedBy = "season")
  private List<DBFixture> fixtures;*/
}
