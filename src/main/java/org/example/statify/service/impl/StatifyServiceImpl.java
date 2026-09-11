package org.example.statify.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.statify.model.TeamGGStatisticsModel;
import org.example.statify.repository.StatifyRepository;
import org.example.statify.service.StatifyService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatifyServiceImpl implements StatifyService {

  private final StatifyRepository statifyRepository;

  @Override
  public List<TeamGGStatisticsModel> getTopSecondHalfGG(Integer lastMatches, Integer limit) {

    return statifyRepository.findTopSecondHalfGG(lastMatches, limit).stream()
        .map(
            result ->
                new TeamGGStatisticsModel(
                    result.getTeamId(),
                    result.getTeamApiId(),
                    result.getTeamName(),
                    result.getGgCount(),
                    result.getMatchesCount(),
                    result.getPercentage()))
        .toList();
  }
}
