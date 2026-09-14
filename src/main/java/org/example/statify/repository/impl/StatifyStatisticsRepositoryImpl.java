package org.example.statify.repository.impl;

import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.statify.entity.enums.ScoreType;
import org.example.statify.model.TeamGGStatisticsModel;
import org.example.statify.repository.StatifyStatisticsRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StatifyStatisticsRepositoryImpl implements StatifyStatisticsRepository {

  private final EntityManager entityManager;

  @Override
  public List<TeamGGStatisticsModel> findTopGG(
      Integer lastMatches, Integer limit, ScoreType scoreType) {
    return List.of();
  }
}
