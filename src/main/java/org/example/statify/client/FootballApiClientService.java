package org.example.statify.client;

import org.example.statify.dto.DTOResponseModel;
import org.example.statify.dto.LeagueSearch;
import org.example.statify.dto.league.LeagueResponseModel;
import org.example.statify.dto.venue.VenueResponseModel;
import org.example.statify.dto.VenueSearch;

public interface FootballApiClientService {

    // DTOResponseModel<LeagueResponseModel> getLeagues();

    DTOResponseModel<LeagueResponseModel> getLeagues(LeagueSearch leagueSearch);

    DTOResponseModel<VenueResponseModel> getVenues(VenueSearch venueSearch);
}
