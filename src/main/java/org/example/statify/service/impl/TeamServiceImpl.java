package org.example.statify.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.statify.client.impl.FootballApiClientImpl;
import org.example.statify.dto.DTOResponseModel;
import org.example.statify.dto.team.TeamApiResponseModel;
import org.example.statify.entity.DBTeam;
import org.example.statify.mapper.TeamMapper;
import org.example.statify.model.TeamModel;
import org.example.statify.repository.TeamRepository;
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
    public void importTeams(String  country) {

        DTOResponseModel<TeamApiResponseModel> response = footballApiClient.getTeamsByCountry(country);

        for (TeamApiResponseModel responseModel : response.getResponse()) {

            TeamModel teamModel = teamMapper.toModel(responseModel);
            if (teamRepository.existsByApiId(teamModel.getApiId())) {
                continue;
            }
            DBTeam team = teamMapper.toEntity(teamModel);

            teamRepository.save(team);
        }
    }
}