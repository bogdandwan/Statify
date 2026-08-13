package org.example.statify.controller;

import lombok.RequiredArgsConstructor;
import org.example.statify.service.FixtureService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FixtureController {

    private final FixtureService fixtureService;


    @PostMapping("/fixtures")
    public void importFixtures(Long leagueId, Integer seasonYear) {
        fixtureService.importFixtures(leagueId, seasonYear);
    }

}
