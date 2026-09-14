package org.example.statify.search;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CoverageSearch {

  private Long id;
  private Boolean standings;
  private Boolean players;
  private Boolean topScorers;
  private Boolean topAssists;
  private Boolean topCards;
  private Boolean injuries;
  private Boolean predictions;
  private Boolean odds;
  private Long seasonId;
  private Integer seasonYear;
  private Long leagueId;
}
