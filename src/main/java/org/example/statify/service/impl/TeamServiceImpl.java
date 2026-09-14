package org.example.statify.service.impl;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.statify.api.ApiResponseModel;
import org.example.statify.api.TeamSearch;
import org.example.statify.api.team.TeamApiResponseModel;
import org.example.statify.client.impl.FootballApiClientImpl;
import org.example.statify.entity.DBTeam;
import org.example.statify.entity.exceptions.NotFoundException;
import org.example.statify.entity.exceptions.ValidationException;
import org.example.statify.mapper.TeamMapper;
import org.example.statify.model.TeamModel;
import org.example.statify.repository.TeamRepository;
import org.example.statify.search.spec.TeamSpec;
import org.example.statify.service.TeamService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

  private final FootballApiClientImpl footballApiClient;
  private final TeamMapper teamMapper;
  private final TeamRepository teamRepository;

  @Transactional
  @Override
  public void importTeams(String country) {

    final TeamSearch search = new TeamSearch().setCountry(country);

    ApiResponseModel<TeamApiResponseModel> response = footballApiClient.getTeamsByCountry(search);

    /*for (TeamApiResponseModel responseModel : response.getResponse()) {
      saveFromApiTeam(responseModel);
    }*/
    response.getResponse().forEach(this::saveFromApiTeam);
  }

  @Override
  public TeamModel getTeamByApiId(Integer teamId) {
    final DBTeam dbTeam = teamRepository.findByApiId(teamId);
    if (dbTeam == null) {
      return saveTeamById(teamId);
    }
    return new TeamModel(dbTeam);
  }

  @Override
  public TeamModel saveTeamById(Integer teamId) {
    final TeamSearch search = new TeamSearch().setId(teamId);
    final ApiResponseModel<TeamApiResponseModel> responseModel =
        footballApiClient.getTeamsByCountry(search);
    if (responseModel == null
        || responseModel.getResponse() == null
        || responseModel.getResponse().size() != 1) {
      throw new ValidationException("Team by id:" + teamId + " not found");
    }
    return saveFromApiTeam(responseModel.getResponse().get(0));
  }

  public TeamModel saveFromApiTeam(TeamApiResponseModel teamApiResponseModel) {
    TeamModel teamModel = teamMapper.toModel(teamApiResponseModel);

    if (teamRepository.existsByApiId(teamModel.getApiId())) {
      return null;
    }

    DBTeam team = teamMapper.toEntity(teamModel);

    DBTeam dbTeam = teamRepository.save(team);
    return new TeamModel(dbTeam);
  }

  @Override
  public List<DBTeam> findAll(org.example.statify.search.TeamSearch search) {
    TeamSpec spec = new TeamSpec(search);

    return teamRepository.findAll(spec);
  }

  @Override
  public DBTeam findById(Long id) {
    return teamRepository.findById(id).orElseThrow(() -> new NotFoundException("Team not found."));
  }

  @Override
  public DBTeam findByApiId(Integer apiId) {
    if (apiId == null) {
      throw new NotFoundException("Team not found.");
    } else {
      return teamRepository.findByApiId(apiId);
    }
  }
}
