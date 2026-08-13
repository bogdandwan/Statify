package org.example.statify.client;

import org.example.statify.dto.ApiResponseModel;
import org.example.statify.dto.CountryResponseModel;
import org.example.statify.dto.LeagueResponseModel;
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


    public ApiResponseModel<LeagueResponseModel> getLeagues() {

        return webClient.get()
                .uri("/leagues")
                .retrieve()
                .bodyToMono(
                        new ParameterizedTypeReference<ApiResponseModel<LeagueResponseModel>>() {}
                )
                .block();
    }

    public ApiResponseModel<CountryModel> getCountries() {

        return webClient.get()
                .uri("/countries")
                .retrieve()
                .bodyToMono(
                        new ParameterizedTypeReference<
                                ApiResponseModel<CountryModel>
                                >() {}
                )
                .block();
    }
}
