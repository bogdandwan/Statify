package org.example.statify.model;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.statify.entity.DBSeason;

@Getter
@Setter
@NoArgsConstructor
public class SeasonModel {

  private Long id;
  private Integer year;
  private LocalDate start;
  private LocalDate end;
  private Boolean current;
  private CoverageModel coverage;
  private LeagueModel league;

  public SeasonModel(DBSeason dbSeason) {
    this.id = dbSeason.getId();
    this.year = dbSeason.getYear();
    this.start = dbSeason.getStart();
    this.end = dbSeason.getEnd();
    this.current = dbSeason.getCurrent();
    if (dbSeason.getCoverage() != null) {
      this.coverage = new CoverageModel();
    }
    if (dbSeason.getLeague() != null) {
      this.league = new LeagueModel(dbSeason.getLeague(), true);
    }
  }
}
