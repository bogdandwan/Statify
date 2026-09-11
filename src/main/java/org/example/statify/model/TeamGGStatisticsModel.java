package org.example.statify.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TeamGGStatisticsModel {

  private Long teamId;
  private Long teamApiId;
  private String teamName;
  private Long ggCount;
  private Long matchesCount;
  private BigDecimal percentage;
}
