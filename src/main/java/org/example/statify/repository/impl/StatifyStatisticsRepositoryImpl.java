package org.example.statify.repository.impl;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.example.statify.entity.enums.ScoreType;
import org.example.statify.model.TeamGGStatisticsModel;
import org.example.statify.repository.StatifyStatisticsRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StatifyStatisticsRepositoryImpl implements StatifyStatisticsRepository {


    private final EntityManager entityManager;


    @Override
    public List<TeamGGStatisticsModel> findTopGG(Integer lastMatches, Integer limit, ScoreType scoreType) {
        return List.of();
    }

}
