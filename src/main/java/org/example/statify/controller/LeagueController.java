package org.example.statify.controller;



import lombok.RequiredArgsConstructor;
import org.example.statify.service.LeagueService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LeagueController {

    private final LeagueService leagueService;

    @PostMapping("/leagues")
    public void importLeagues() {
        leagueService.importLeagues();
    }
}
