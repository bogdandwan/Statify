package org.example.statify.service;

import java.util.List;
import org.example.statify.api.team.TeamApiResponseModel;
import org.example.statify.model.TeamModel;
import org.example.statify.search.TeamSearch;

public interface TeamService {

  void importTeams(String country);

  TeamModel getTeamByApiId(Integer awayTeamId);

  TeamModel saveTeamById(Integer awayTeamId);

  TeamModel saveFromApiTeam(TeamApiResponseModel teamApiResponseModel);

  List<TeamModel> findAll(TeamSearch search);

  TeamModel findById(Long id);

  TeamModel findByApiId(Integer apiId);

  void importAllTeams();
}
