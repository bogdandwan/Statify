package org.example.statify.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.statify.model.TeamGGStatisticsModel;
import org.example.statify.repository.StatifyStatisticsRepository;
import org.example.statify.search.GGStatisticsSearch;
import org.example.statify.service.StatisticsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

  private final StatifyStatisticsRepository repository;

  @Override
  public List<TeamGGStatisticsModel> findGGStatistics(GGStatisticsSearch search) {

    return repository.findGGStatistics(search);
  }
}
