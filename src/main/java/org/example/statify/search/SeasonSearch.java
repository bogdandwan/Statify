package org.example.statify.search;

import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class SeasonSearch {

  private Long id;
  private Integer year;
  private Integer yearFrom;
  private Integer yearTo;
  private LocalDate startFrom;
  private LocalDate startTo;
  private LocalDate endFrom;
  private LocalDate endTo;
  private Boolean current;
  private Long leagueId;
  private Integer leagueApiId;
}
