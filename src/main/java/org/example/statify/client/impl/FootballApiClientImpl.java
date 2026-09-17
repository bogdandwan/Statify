package org.example.statify.client.impl;

import java.time.Duration;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.example.statify.api.*;
import org.example.statify.api.fixture.FixtureResponseModel;
import org.example.statify.api.league.LeagueResponseModel;
import org.example.statify.api.player.PlayerResponseModel;
import org.example.statify.api.team.TeamApiResponseModel;
import org.example.statify.api.venue.VenueResponseModel;
import org.example.statify.client.FootballApiClientService;
import org.example.statify.model.CountryModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Slf4j
@Service
public class FootballApiClientImpl implements FootballApiClientService {

  private final WebClient webClient;
  private final String apiKey;
  private static final String LEAGUES_API = "/leagues";
  private static final String VENUES_API = "/venues";
  private static final String COUNTRIES_API = "/countries";
  private static final String TEAMS_API = "/teams";
  private static final String FIXTURE_API = "/fixtures";
  private static final String PLAYERS_API = "/players";

  private static final long MIN_REQUEST_INTERVAL_MS = 150;

  private long lastRequestTime = 0;

  public FootballApiClientImpl(
      WebClient.Builder builder, @Value("${football.api.key}") String apiKey) {
    this.apiKey = apiKey;
    this.webClient =
        builder
            .baseUrl("https://v3.football.api-sports.io")
            .codecs(
                configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024 * 10)) // 10 MB
            .defaultHeader("x-apisports-key", apiKey)
            .build();
  }

  private synchronized void waitForRateLimit() {

    long now = System.currentTimeMillis();

    long elapsed = now - lastRequestTime;

    if (elapsed < MIN_REQUEST_INTERVAL_MS) {

      long waitTime = MIN_REQUEST_INTERVAL_MS - elapsed;

      try {
        System.out.println("RATE LIMIT - WAITING: " + waitTime + " ms");

        Thread.sleep(waitTime);

      } catch (InterruptedException e) {

        Thread.currentThread().interrupt();

        throw new RuntimeException("Thread interrupted while waiting for API rate limit", e);
      }
    }

    lastRequestTime = System.currentTimeMillis();
  }

  private <T> T get(String uri, ParameterizedTypeReference<T> responseType) {

    return Mono.defer(
            () -> {
              waitForRateLimit();

              return webClient.get().uri(uri).retrieve().bodyToMono(responseType);
            })
        .retryWhen(
            Retry.backoff(5, Duration.ofSeconds(2))
                .maxBackoff(Duration.ofSeconds(30))
                .filter(this::isRetryableException)
                .doBeforeRetry(
                    retrySignal ->
                        log.warn(
                            "API request failed. Retrying... attempt = {} | uri = {} | error = {}",
                            retrySignal.totalRetries() + 1,
                            uri,
                            retrySignal.failure().getMessage()))
                .onRetryExhaustedThrow((retrySpec, retrySignal) -> retrySignal.failure()))
        .block();
  }

  private boolean isRetryableException(Throwable throwable) {

    if (throwable instanceof WebClientRequestException) {
      return true;
    }

    if (throwable instanceof WebClientResponseException exception) {

      return exception.getStatusCode().is5xxServerError()
          || exception.getStatusCode().value() == 429;
    }

    return false;
  }

  @Override
  public ApiResponseModel<LeagueResponseModel> getLeagues(ApiLeagueSearch search) {
    return webClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
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
        .bodyToMono(new ParameterizedTypeReference<ApiResponseModel<LeagueResponseModel>>() {})
        .block();
  }

  @Override
  public ApiResponseModel<VenueResponseModel> getVenues(ApiVenueSearch search) {
    return webClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
                    .path(VENUES_API)
                    .queryParamIfPresent("country", Optional.ofNullable(search.getCountry()))
                    .queryParamIfPresent("city", Optional.ofNullable(search.getCity()))
                    .queryParamIfPresent("search", Optional.ofNullable(search.getFullText()))
                    .queryParamIfPresent("name", Optional.ofNullable(search.getName()))
                    .queryParamIfPresent("id", Optional.ofNullable(search.getId()))
                    .build())
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<ApiResponseModel<VenueResponseModel>>() {})
        .block();
  }

  public ApiResponseModel<CountryModel> getCountries(ApiCountrySearch search) {

    return webClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
                    .path(COUNTRIES_API)
                    .queryParamIfPresent("name", Optional.ofNullable(search.getName()))
                    .queryParamIfPresent("code", Optional.ofNullable(search.getCode()))
                    .queryParamIfPresent("search", Optional.ofNullable(search.getFullText()))
                    .build())
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<ApiResponseModel<CountryModel>>() {})
        .block();
  }

  public ApiResponseModel<FixtureResponseModel> getFixtures(ApiFixtureSearch search) {

    return webClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
                    .path(FIXTURE_API)
                    .queryParamIfPresent("id", Optional.ofNullable(search.getId()))
                    .queryParamIfPresent("league", Optional.ofNullable(search.getLeague()))
                    .queryParamIfPresent("season", Optional.ofNullable(search.getSeason()))
                    .queryParamIfPresent("team", Optional.ofNullable(search.getTeam()))
                    .queryParamIfPresent("date", Optional.ofNullable(search.getDate()))
                    .queryParamIfPresent("from", Optional.ofNullable(search.getFrom()))
                    .queryParamIfPresent("to", Optional.ofNullable(search.getTo()))
                    .queryParamIfPresent("status", Optional.ofNullable(search.getStatus()))
                    .queryParamIfPresent("timezone", Optional.ofNullable(search.getTimezone()))
                    .queryParamIfPresent("last", Optional.ofNullable(search.getLast()))
                    .queryParamIfPresent("next", Optional.ofNullable(search.getNext()))
                    .build())
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<ApiResponseModel<FixtureResponseModel>>() {})
        .block();
  }

  public ApiResponseModel<TeamApiResponseModel> getTeamsByCountry(ApiTeamSearch search) {

    return webClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
                    .path(TEAMS_API)
                    .queryParamIfPresent("id", Optional.ofNullable(search.getId()))
                    .queryParamIfPresent("name", Optional.ofNullable(search.getName()))
                    .queryParamIfPresent("league", Optional.ofNullable(search.getLeague()))
                    .queryParamIfPresent("season", Optional.ofNullable(search.getSeason()))
                    .queryParamIfPresent("country", Optional.ofNullable(search.getCountry()))
                    .queryParamIfPresent("code", Optional.ofNullable(search.getCode()))
                    .queryParamIfPresent("search", Optional.ofNullable(search.getFullText()))
                    .build())
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<ApiResponseModel<TeamApiResponseModel>>() {})
        .block();
  }

  public ApiResponseModel<PlayerResponseModel> getPlayers(ApiPlayerSearch search) {

    return webClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
                    .path(PLAYERS_API)
                    .queryParamIfPresent("id", Optional.ofNullable(search.getId()))
                    .queryParamIfPresent("team", Optional.ofNullable(search.getTeam()))
                    .queryParamIfPresent("league", Optional.ofNullable(search.getLeague()))
                    .queryParamIfPresent("season", Optional.ofNullable(search.getSeason()))
                    .queryParamIfPresent("search", Optional.ofNullable(search.getFullText()))
                    .queryParamIfPresent("page", Optional.ofNullable(search.getPage()))
                    .build())
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<ApiResponseModel<PlayerResponseModel>>() {})
        .block();
  }
}
