package org.example.statify.client;

import org.example.statify.api.*;
import org.example.statify.api.fixture.FixtureResponseModel;
import org.example.statify.api.league.LeagueResponseModel;
import org.example.statify.api.player.PlayerResponseModel;
import org.example.statify.api.team.TeamApiResponseModel;
import org.example.statify.api.venue.VenueResponseModel;
import org.example.statify.model.CountryModel;

public interface FootballApiClientService {

  ApiResponseModel<LeagueResponseModel> getLeagues(ApiLeagueSearch leagueSearch);

  ApiResponseModel<VenueResponseModel> getVenues(ApiVenueSearch venueSearch);

  ApiResponseModel<TeamApiResponseModel> getTeamsByCountry(ApiTeamSearch teamSearch);

  ApiResponseModel<CountryModel> getCountries(ApiCountrySearch search);

  ApiResponseModel<FixtureResponseModel> getFixtures(ApiFixtureSearch fixtureSearch);

  ApiResponseModel<PlayerResponseModel> getPlayers(ApiPlayerSearch search);
}
