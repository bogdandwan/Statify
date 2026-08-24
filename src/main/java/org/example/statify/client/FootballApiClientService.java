package org.example.statify.client;

import org.example.statify.api.*;
import org.example.statify.api.country.CountryResponseModel;
import org.example.statify.api.league.LeagueResponseModel;
import org.example.statify.api.team.TeamApiResponseModel;
import org.example.statify.api.venue.VenueResponseModel;
import org.example.statify.model.CountryModel;

public interface FootballApiClientService {


    ApiResponseModel<LeagueResponseModel> getLeagues(LeagueSearch leagueSearch);

    ApiResponseModel<VenueResponseModel> getVenues(VenueSearch venueSearch);

    ApiResponseModel<TeamApiResponseModel>  getTeamsByCountry(TeamSearch teamSearch);

    ApiResponseModel<CountryModel> getCountries(CountrySearch search);
}
