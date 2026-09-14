package org.example.statify.search;

import java.time.OffsetDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FixtureSearch {

  private Long id;
  private Long apiId;

  private String referee;
  private String timezone;

  private OffsetDateTime dateFrom;
  private OffsetDateTime dateTo;

  private String statusLong;
  private String statusShort;

  private Long leagueId;
  private Integer leagueApiId;

  private Long seasonId;
  private Integer seasonYear;

  private Long venueId;
  private Long venueApiId;

  private Long homeTeamId;
  private Long awayTeamId;

  // ako hoćemo utakmice gde je tim bilo home bilo away
  private Long teamId;

  private Long homeTeamApiId;
  private Long awayTeamApiId;
  private Long teamApiId;
}
