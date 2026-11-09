package org.example.statify.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.statify.api.ApiLeagueSearch;
import org.example.statify.api.league.LeagueResponseModel;
import org.example.statify.api.season.SeasonResponseModel;
import org.example.statify.client.impl.FootballApiClientImpl;
import org.example.statify.entity.DBSeason;
import org.example.statify.mapper.SeasonMapper;
import org.example.statify.model.LeagueModel;
import org.example.statify.model.SeasonModel;
import org.example.statify.repository.SeasonRepository;
import org.example.statify.search.SeasonSearch;
import org.example.statify.search.spec.SeasonSpec;
import org.example.statify.service.SeasonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SeasonServiceImpl implements SeasonService {

  private final SeasonRepository seasonRepository;
  private final FootballApiClientImpl footballApiClient;
  private final SeasonMapper seasonMapper;

  @Override
  @Transactional(readOnly = true)
  public List<SeasonModel> findAll(SeasonSearch search) {

    final List<DBSeason> dbSeasons = seasonRepository.findAll(new SeasonSpec(search));

    return dbSeasons.stream().map(SeasonModel::new).collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public List<SeasonModel> findCurrentSeasons() {
    return findAll(new SeasonSearch().setCurrent(true));
  }

  @Override
  @Transactional(readOnly = true)
  public SeasonModel findByLeagueAndYear(LeagueModel league, Integer seasonYear) {
    DBSeason dbSeason = seasonRepository.findByLeague_IdAndYear(league.getId(), seasonYear);
    if (dbSeason == null) {
      final LeagueResponseModel leagueResponseModel =
          footballApiClient
              .getLeagues(new ApiLeagueSearch().setId(league.getApiId()))
              .getResponse()
              .stream()
              .findFirst()
              .orElse(null);
      final SeasonResponseModel seasonResponseModel =
          leagueResponseModel.getSeasons().stream()
              .filter(s -> Objects.equals(s.getYear(), seasonYear))
              .toList()
              .stream()
              .findFirst()
              .orElse(null);
      final SeasonModel seasonModel = seasonMapper.toModel(seasonResponseModel);
      dbSeason = seasonMapper.toEntity(seasonModel);
      return new SeasonModel(seasonRepository.save(dbSeason));

    } else {
      return new SeasonModel(dbSeason);
    }
  }
}
