package org.example.statify.scheduler;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.statify.model.SeasonModel;
import org.example.statify.service.FixtureService;
import org.example.statify.service.SeasonService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpcomingFixtureScheduler {

  private final FixtureService fixtureService;
  private final SeasonService seasonService;

  // @Scheduled(fixedRate = 30 * 60 * 1000, initialDelay = 0)
  @Transactional
  public void updateFixtures() {

    System.out.println("FIXTURE SCHEDULER STARTED");

    List<SeasonModel> currentSeasons = seasonService.findCurrentSeasons();

    for (SeasonModel season : currentSeasons) {

      Integer leagueApiId = season.getLeague().getApiId();
      Integer seasonYear = season.getYear();

      System.out.println("UPDATING LEAGUE = " + leagueApiId + " | SEASON = " + seasonYear);

      fixtureService.importFixtures(leagueApiId, seasonYear);
    }

    System.out.println("FIXTURE SCHEDULER FINISHED");
  }
}
