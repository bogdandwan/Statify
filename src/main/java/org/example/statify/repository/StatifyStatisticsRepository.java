package org.example.statify.repository;

import java.util.List;
import org.example.statify.entity.enums.ScoreType;
import org.example.statify.model.TeamGGStatisticsModel;

public interface StatifyStatisticsRepository {

  List<TeamGGStatisticsModel> findTopGG(Integer lastMatches, Integer limit, ScoreType scoreType);
}
