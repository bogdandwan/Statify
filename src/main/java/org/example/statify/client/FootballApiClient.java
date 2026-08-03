package org.example.statify.client;

import org.example.statify.dto.ApiResponseDto;
import org.example.statify.dto.LeagueResponseDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class FootballApiClient {

    private final WebClient webClient;

    public FootballApiClient(WebClient.Builder builder) {

        this.webClient = builder
                .baseUrl("https://v3.football.api-sports.io")
                .defaultHeader("x-apisports-key", "TVOJ_API_KEY")
                .build();
    }


    public ApiResponseDto<LeagueResponseDto> getLeagues() {

        return webClient.get()
                .uri("/leagues")
                .retrieve()
                .bodyToMono(
                        new ParameterizedTypeReference<ApiResponseDto<LeagueResponseDto>>() {}
                )
                .block();
    }
}
