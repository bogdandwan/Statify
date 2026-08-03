package org.example.statify.service;

import lombok.RequiredArgsConstructor;
import org.example.statify.client.FootballApiClient;
import org.example.statify.dto.ApiResponseDto;
import org.example.statify.dto.LeagueResponseDto;
import org.example.statify.entity.DBLeague;
import org.example.statify.mapper.LeagueMapper;
import org.example.statify.repository.LeagueRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeagueImportService {


    private final FootballApiClient footballApiClient;
    private final LeagueMapper leagueMapper;
    private final LeagueRepository leagueRepository;


    public LeagueImportService(
            FootballApiClient footballApiClient,
            LeagueMapper leagueMapper,
            LeagueRepository leagueRepository
    ) {
        this.footballApiClient = footballApiClient;
        this.leagueMapper = leagueMapper;
        this.leagueRepository = leagueRepository;
    }



    public void importLeagues() {


        ApiResponseDto<LeagueResponseDto> response =
                footballApiClient.getLeagues();



        List<DBLeague> leagues =
                response.getResponse()
                        .stream()
                        .map(leagueMapper::toModel)
                        .map(leagueMapper::toEntity)
                        .toList();



        leagueRepository.saveAll(leagues);
    }
}
