package org.example.statify.service;

import org.example.statify.model.LeagueModel;
import org.example.statify.model.VenueModel;

public interface LeagueService {

    void importLeagues();

    LeagueModel saveLeagueById(Integer leagueId);

    LeagueModel getLeagueByApiId(Integer leagueId);
}
