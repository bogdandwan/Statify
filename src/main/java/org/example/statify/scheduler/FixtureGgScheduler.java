package org.example.statify.scheduler;

import java.time.LocalDate;
import java.time.ZoneId;
import lombok.RequiredArgsConstructor;
import org.example.statify.service.FixtureService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FixtureGgScheduler {

  private final FixtureService fixtureService;

  @Value("${scheduler.zone}")
  private String schedulerZone;

  @Scheduled(cron = "${scheduler.gg.cron}", zone = "${scheduler.zone}")
  public void calculateYesterdayGg() {

    LocalDate yesterday = LocalDate.now(ZoneId.of(schedulerZone)).minusDays(1);

    System.out.println("STARTING GG SCHEDULER FOR: " + yesterday);

    fixtureService.calculateGgForDate(yesterday);

    System.out.println("GG SCHEDULER FINISHED FOR: " + yesterday);
  }
}
