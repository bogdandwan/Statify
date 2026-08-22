package org.example.statify.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.statify.client.impl.FootballApiClientImpl;
import org.example.statify.dto.DTOResponseModel;
import org.example.statify.dto.player.PlayerResponseModel;
import org.example.statify.entity.DBPlayer;
import org.example.statify.mapper.PlayerMapper;
import org.example.statify.model.PlayerModel;
import org.example.statify.repository.PlayerRepository;
import org.example.statify.service.PlayerService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;
    private final FootballApiClientImpl footballApiClient;

    @Transactional
    @Override
    public void importPlayer(Long leagueId, Integer seasonYear) {
        int page = 1;
        int totalPages;

        do {
            DTOResponseModel<PlayerResponseModel> response = footballApiClient.getPlayers(leagueId, seasonYear, page);

            for (PlayerResponseModel responseModel : response.getResponse()) {

                PlayerModel playerModel = playerMapper.toModel(responseModel);
                if (playerRepository.findByApiId(playerModel.getId()).isPresent()) {
                    continue;
                }
                DBPlayer player = playerMapper.toEntity(playerModel);

                playerRepository.save(player);
            }

            totalPages = response.getPaging().getTotal();
            page++;

        } while (page <= totalPages);
    }
}
