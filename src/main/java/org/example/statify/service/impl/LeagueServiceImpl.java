package org.example.statify.service.impl;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.statify.api.ApiResponseModel;
import org.example.statify.api.LeagueSearch;
import org.example.statify.api.league.LeagueResponseModel;
import org.example.statify.client.impl.FootballApiClientImpl;
import org.example.statify.entity.DBCountry;
import org.example.statify.entity.DBLeague;
import org.example.statify.entity.exceptions.NotFoundException;
import org.example.statify.entity.exceptions.ValidationException;
import org.example.statify.mapper.LeagueMapper;
import org.example.statify.model.LeagueModel;
import org.example.statify.repository.CountryRepository;
import org.example.statify.repository.LeagueRepository;
import org.example.statify.search.spec.LeagueSpec;
import org.example.statify.service.LeagueService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeagueServiceImpl implements LeagueService {

  private final FootballApiClientImpl footballApiClient;
  private final LeagueMapper leagueMapper;
  private final LeagueRepository leagueRepository;
  private final CountryRepository countryRepository;

  @Transactional
  public void importLeagues() {

    ApiResponseModel<LeagueResponseModel> response =
        footballApiClient.getLeagues(new LeagueSearch());

    // response.getResponse().forEach(leagueResponseModel ->
    // saveFromApiLeague(leagueResponseModel));
    // response.getResponse().forEach(leagueResponseModel ->
    // {saveFromApiLeague(leagueResponseModel);});
    response.getResponse().forEach(this::saveFromApiLeague);
  }

  private LeagueModel saveFromApiLeague(LeagueResponseModel leagueResponseModel) {

    LeagueModel leagueModel = leagueMapper.toModel(leagueResponseModel);
    if (leagueRepository.existsByApiId(leagueModel.getApiId())) {
      return null;
    }

    DBLeague league = leagueMapper.toEntity(leagueModel);
    if (leagueModel.getCountry() != null) {
      String countryName = leagueModel.getCountry().getName();
      DBCountry country = countryRepository.findByName(countryName);

      league.setCountry(country);
    }
    DBLeague dbLeague = leagueRepository.save(league);

    return new LeagueModel(dbLeague);
  }

  @Override
  @Transactional
  public LeagueModel getLeagueByApiId(Integer leagueId) {
    final DBLeague dbLeague = leagueRepository.findByApiId(leagueId);
    if (dbLeague == null) {
      return saveLeagueById(leagueId);
    }
    return new LeagueModel(dbLeague);
  }

  public LeagueModel saveLeagueById(Integer leagueId) {
    final LeagueSearch search = new LeagueSearch().setId(leagueId);
    final ApiResponseModel<LeagueResponseModel> responseModel =
        footballApiClient.getLeagues(search);
    if (responseModel == null
        || responseModel.getResponse() == null
        || responseModel.getResponse().size() != 1) {
      throw new ValidationException("League by id:" + leagueId + " not found");
    }
    return saveFromApiLeague(responseModel.getResponse().get(0));
  }

  @Override
  public List<DBLeague> findAll(org.example.statify.search.LeagueSearch search) {

    return leagueRepository.findAll(new LeagueSpec(search));
  }

  @Override
  public DBLeague findById(Long id) {

    return leagueRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("League not found."));
  }

  @Override
  public DBLeague findByApiId(Integer apiId) {

    if (apiId == null) {
      throw new NotFoundException("League not found.");
    } else {
      return leagueRepository.findByApiId(apiId);
    }
  }
}
