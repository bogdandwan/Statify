package org.example.statify.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.statify.entity.DBSeason;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class SeasonModel {

    private Integer year;
    private LocalDate start;
    private LocalDate end;
    private Boolean current;
    private CoverageModel coverage;

    public SeasonModel(DBSeason dbSeason) {
        this.year = dbSeason.getYear();
        this.start = dbSeason.getStart();
        this.end = dbSeason.getEnd();
        this.current = dbSeason.getCurrent();
        if (dbSeason.getCoverage() != null) {
            this.coverage = new CoverageModel();
        }
    }
}
