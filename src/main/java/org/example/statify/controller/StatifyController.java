package org.example.statify.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.statify.model.TeamGGStatisticsModel;
import org.example.statify.search.GGStatisticsSearch;
import org.example.statify.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatifyController {

  private final StatisticsService statisticsService;

  @GetMapping("/gg")
  public List<TeamGGStatisticsModel> getGGStatistics(GGStatisticsSearch search) {

    return statisticsService.findGGStatistics(search);
  }
}
