package org.example.statify.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.statify.model.FixtureModel;
import org.example.statify.search.FixtureSearch;
import org.example.statify.service.FixtureService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FixtureController {

  private final FixtureService fixtureService;

  @GetMapping("/fixtures")
  public List<FixtureModel> findAll(FixtureSearch search) {
    return fixtureService.findAll(search);
  }

  @PostMapping("/fixtures/sync-upcoming")
  public void syncUpcomingFixtures() {
    fixtureService.syncUpcomingFixtures();
  }
}
