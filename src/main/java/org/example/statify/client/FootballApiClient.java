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
                .defaultHeader("x-apisports-key", "eb1df6a10c02c5e8cf95c849183a63a5")
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
