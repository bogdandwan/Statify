package org.example.statify.client.impl;

import org.example.statify.client.FootballApiClientService;
import org.example.statify.dto.DTOResponseModel;
import org.example.statify.dto.LeagueSearch;
import org.example.statify.dto.league.LeagueResponseModel;
import org.example.statify.dto.VenueSearch;
import org.example.statify.dto.fixture.FixtureResponseModel;
import org.example.statify.dto.fixture.FixtureSearch;
import org.example.statify.dto.player.PlayerResponseModel;
import org.example.statify.dto.team.TeamApiResponseModel;
import org.example.statify.dto.venue.VenueResponseModel;
import org.example.statify.model.CountryModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
public class FootballApiClientImpl implements FootballApiClientService {

    private final WebClient webClient;
    private final String apiKey;
    private final static String LEAGUES_API =  "/leagues";
    private final static String VENUES_API = "/venues";


    public FootballApiClientImpl(WebClient.Builder builder,
                                 @Value("${football.api.key}") String apiKey) {
        this.apiKey = apiKey;
        this.webClient = builder
                .baseUrl("https://v3.football.api-sports.io")
                .codecs(configurer ->
                        configurer.defaultCodecs()
                                .maxInMemorySize(1024 * 1024 * 10)) // 10 MB
                .defaultHeader("x-apisports-key", apiKey)
                .build();
    }


    /*public DTOResponseModel<LeagueResponseModel> getLeagues() {
        return get(
                LEAGUES_API,
                new ParameterizedTypeReference<DTOResponseModel<LeagueResponseModel>>() {}
        );
    }*/

    @Override
    public DTOResponseModel<LeagueResponseModel> getLeagues(LeagueSearch search) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(LEAGUES_API)
                        .queryParamIfPresent("id", Optional.ofNullable(search.getId()))
                        .queryParamIfPresent("name", Optional.ofNullable(search.getName()))
                        .queryParamIfPresent("country", Optional.ofNullable(search.getCountry()))
                        .queryParamIfPresent("code", Optional.ofNullable(search.getCode()))
                        .queryParamIfPresent("season", Optional.ofNullable(search.getSeason()))
                        .queryParamIfPresent("team", Optional.ofNullable(search.getTeam()))
                        .queryParamIfPresent("type", Optional.ofNullable(search.getType()))
                        .queryParamIfPresent("current", Optional.ofNullable(search.getCurrent()))
                        .queryParamIfPresent("search", Optional.ofNullable(search.getFullText()))
                        .queryParamIfPresent("last", Optional.ofNullable(search.getLast()))
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<DTOResponseModel<LeagueResponseModel>>() {})
                .block();
    }

    @Override
    public DTOResponseModel<VenueResponseModel> getVenues(VenueSearch search) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(VENUES_API)
                        .queryParamIfPresent("country", Optional.ofNullable(search.getCountry()))
                        .queryParamIfPresent("city", Optional.ofNullable(search.getCity()))
                        .queryParamIfPresent("search", Optional.ofNullable(search.getFullText()))
                        .queryParamIfPresent("name", Optional.ofNullable(search.getName()))
                        .queryParamIfPresent("id", Optional.ofNullable(search.getId()))
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<DTOResponseModel<VenueResponseModel>>() {})
                .block();
    }

    public DTOResponseModel<CountryModel> getCountries() {

        return webClient.get()
                .uri("/countries")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<DTOResponseModel<CountryModel>>() {})
                .block();
    }

    public DTOResponseModel<FixtureResponseModel> getFixtures(FixtureSearch fixtureSearch) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/fixtures")
                        .queryParamIfPresent("league", Optional.ofNullable(fixtureSearch.getLeagueId()))
                        .queryParamIfPresent("season", Optional.ofNullable(fixtureSearch.getSeasonYear()))
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<DTOResponseModel<FixtureResponseModel>>() {})
                .block();
    }

    public DTOResponseModel<TeamApiResponseModel> getTeamsByCountry(String country) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/teams")
                        .queryParam("country", country)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<DTOResponseModel<TeamApiResponseModel>>() {})
                .block();
    }

    public DTOResponseModel<PlayerResponseModel> getPlayers(Long leagueId, Integer seasonYear, Integer page) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/players")
                        .queryParam("league", leagueId)
                        .queryParam("season", seasonYear)
                        .queryParam("page", page)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<DTOResponseModel<PlayerResponseModel>>() {})
                .block();
    }

    private <T> T get(String uri, ParameterizedTypeReference<T> responseType) {

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }


}
