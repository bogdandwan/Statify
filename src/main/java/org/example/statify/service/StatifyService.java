package org.example.statify.service;

import java.util.List;
import org.example.statify.model.TeamGGStatisticsModel;

public interface StatifyService {

  List<TeamGGStatisticsModel> getTopSecondHalfGG(Integer lastMatches, Integer limit);
}
