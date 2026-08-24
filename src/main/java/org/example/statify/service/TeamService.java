package org.example.statify.service;

import org.example.statify.api.team.TeamApiResponseModel;
import org.example.statify.model.TeamModel;

public interface TeamService {

    void importTeams(String country);

    TeamModel getTeamByApiId(Integer awayTeamId);

    TeamModel saveTeamById(Integer awayTeamId);

    TeamModel saveFromApiTeam(TeamApiResponseModel teamApiResponseModel);
}
