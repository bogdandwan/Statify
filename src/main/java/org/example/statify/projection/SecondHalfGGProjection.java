package org.example.statify.projection;

import java.math.BigDecimal;

public interface SecondHalfGGProjection {

  Long getTeamId();

  Integer getTeamApiId();

  String getTeamName();

  Long getGgCount();

  Long getMatchesCount();

  BigDecimal getPercentage();
}
