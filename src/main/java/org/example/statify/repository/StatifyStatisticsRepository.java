package org.example.statify.repository;

import org.example.statify.entity.enums.ScoreType;
import org.example.statify.model.TeamGGStatisticsModel;

import java.util.List;

public interface StatifyStatisticsRepository {

    List<TeamGGStatisticsModel> findTopGG(Integer lastMatches, Integer limit, ScoreType scoreType);

}
