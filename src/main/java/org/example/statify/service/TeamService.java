package org.example.statify.service;

import java.util.List;
import org.example.statify.api.team.TeamApiResponseModel;
import org.example.statify.entity.DBTeam;
import org.example.statify.model.TeamModel;
import org.example.statify.search.TeamSearch;

public interface TeamService {

  void importTeams(String country);

  TeamModel getTeamByApiId(Integer awayTeamId);

  TeamModel saveTeamById(Integer awayTeamId);

  TeamModel saveFromApiTeam(TeamApiResponseModel teamApiResponseModel);

  List<DBTeam> findAll(TeamSearch search);

  DBTeam findById(Long id);

  DBTeam findByApiId(Integer apiId);
}
