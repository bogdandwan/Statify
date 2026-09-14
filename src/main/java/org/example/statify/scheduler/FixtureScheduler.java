package org.example.statify.scheduler;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.statify.entity.DBSeason;
import org.example.statify.repository.SeasonRepository;
import org.example.statify.service.FixtureService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FixtureScheduler {

  private final FixtureService fixtureService;
  private final SeasonRepository seasonRepository;

  @Scheduled(cron = "0 */30 * * * *")
  public void updateFixtures() {

    System.out.println("FIXTURE SCHEDULER STARTED");

    List<DBSeason> currentSeasons = seasonRepository.findAllByCurrentTrue();

    for (DBSeason season : currentSeasons) {

      Integer leagueApiId = season.getLeague().getApiId();
      Integer seasonYear = season.getYear();

      System.out.println("UPDATING LEAGUE = " + leagueApiId + " | SEASON = " + seasonYear);

      fixtureService.importFixtures(leagueApiId, seasonYear);
    }

    System.out.println("FIXTURE SCHEDULER FINISHED");
  }
}
