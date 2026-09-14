package org.example.statify.search;

import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.example.statify.entity.enums.ScoreType;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class GGStatisticsSearch {

  private Integer leagueApiId;
  private Integer seasonYear;

  private ScoreType scoreType;

  // null = sve utakmice
  // 5 = poslednjih 5 po timu
  // 10 = poslednjih 10 po timu
  private Integer lastMatches;

  // null = vrati sve timove
  // 10 = top 10
  private Integer limit;

  private OffsetDateTime dateFrom;
  private OffsetDateTime dateTo;
}
