package org.example.statify.client;

import org.example.statify.dto.DTOResponseModel;
import org.example.statify.dto.LeagueResponseModel;
import org.example.statify.dto.fixture.FixtureResponseModel;
import org.example.statify.dto.team.TeamApiResponseModel;
import org.example.statify.dto.team.TeamResponseModel;
import org.example.statify.dto.VenueResponseModel;
import org.example.statify.dto.fixture.FixtureResponseModel;
import org.example.statify.model.CountryModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class FootballApiClient {

    private final WebClient webClient;
    private final String apiKey;


    public FootballApiClient(WebClient.Builder builder,
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


    public DTOResponseModel<LeagueResponseModel> getLeagues() {

        return webClient.get()
                .uri("/leagues")
                .retrieve()
                .bodyToMono(
                        new ParameterizedTypeReference<DTOResponseModel<LeagueResponseModel>>() {}
                )
                .block();
    }

    public DTOResponseModel<CountryModel> getCountries() {

        return webClient.get()
                .uri("/countries")
                .retrieve()
                .bodyToMono(
                        new ParameterizedTypeReference<
                                DTOResponseModel<CountryModel>
                                >() {}
                )
                .block();
    }
    public DTOResponseModel<FixtureResponseModel> getFixtures(
            Long leagueId,
            Integer seasonYear) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/fixtures")
                        .queryParam("league", 39)
                        .queryParam("season", 2024)
                        .build()
                )
                .retrieve()
                .bodyToMono(
                        new ParameterizedTypeReference<
                                DTOResponseModel<FixtureResponseModel>
                                >() {}
                )
                .block();
    }

    public DTOResponseModel<TeamApiResponseModel> getTeamsByCountry(String country) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/teams")
                        .queryParam("country", country)
                        .build()
                )
                .retrieve()
                .bodyToMono(
                        new ParameterizedTypeReference<
                                DTOResponseModel<TeamApiResponseModel>
                                >() {}
                )
                .block();
    }

    public DTOResponseModel<VenueResponseModel> getVenuesByCountry(String country) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/venues")
                        .queryParam("country", country)
                        .build()
                )
                .retrieve()
                .bodyToMono(
                        new ParameterizedTypeReference<
                                DTOResponseModel<VenueResponseModel>
                                >() {}
                )
                .block();
    }
}
