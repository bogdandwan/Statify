package org.example.statify.service;

import java.util.List;
import org.example.statify.model.TeamGGStatisticsModel;
import org.example.statify.search.GGStatisticsSearch;

public interface StatisticsService {

  List<TeamGGStatisticsModel> findGGStatistics(GGStatisticsSearch search);
}
