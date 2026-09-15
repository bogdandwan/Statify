package org.example.statify.controller;

import lombok.RequiredArgsConstructor;
import org.example.statify.service.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CodebookController {

  private final CountryService countryService;
  private final FixtureService fixtureService;
  private final LeagueService leagueService;
  private final TeamService teamService;
  private final VenueService venueService;
  private final PlayerService playerService;

  @PostMapping("/country")
  public void importCountries(String name) {
    countryService.importCountries(name);
  }

  @PostMapping("/fixtures")
  public void importFixtures(
      @RequestParam(value = "league", required = false) Integer leagueId,
      @RequestParam(value = "season", required = false) Integer seasonYear) {
    fixtureService.importFixtures(leagueId, seasonYear);
  }

  @PostMapping("/leagues")
  public void importLeagues() {
    leagueService.importLeagues();
  }

  @PostMapping("/teams")
  public void importTeams(String country) {
    teamService.importTeams(country);
  }

  @PostMapping("/venues")
  public void importVenue(String country) {
    venueService.importVenues(country);
  }

  @PostMapping("/players")
  public void importPlayers(@RequestParam Integer leagueId, @RequestParam Integer season) {
    playerService.importPlayer(leagueId, season);
  }
}
