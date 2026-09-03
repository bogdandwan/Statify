package org.example.statify.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.statify.api.PlayerSearch;
import org.example.statify.client.impl.FootballApiClientImpl;
import org.example.statify.api.ApiResponseModel;
import org.example.statify.api.player.PlayerResponseModel;
import org.example.statify.entity.DBPlayer;
import org.example.statify.entity.exceptions.ValidationException;
import org.example.statify.mapper.PlayerMapper;
import org.example.statify.model.PlayerModel;
import org.example.statify.model.VenueModel;
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
    public void importPlayer(Integer leagueId, Integer seasonYear) {
        int page = 1;
        int totalPages;

        do {
            final PlayerSearch search = new PlayerSearch()
                    .setLeague(leagueId)
                    .setSeason(seasonYear)
                    .setPage(page);
            ApiResponseModel<PlayerResponseModel> response = footballApiClient.getPlayers(search);

            for (PlayerResponseModel responseModel : response.getResponse()) {

                PlayerModel playerModel = playerMapper.toModel(responseModel);
                if (playerRepository.findByApiId(playerModel.getApiId()) != null) {
                    continue;
                }
                saveFromApiPlayer(responseModel);
            }

            totalPages = response.getPaging().getTotal();
            page++;

        } while (page <= totalPages);
    }

    public PlayerModel getPlayerByApiId(Integer playerId) {
        final DBPlayer dbPlayer = playerRepository.findByApiId(playerId);
        if (dbPlayer == null) {
            return savePlayerById(playerId);
        }
        return new PlayerModel(dbPlayer);
    }

    public PlayerModel savePlayerById(Integer playerId) {
        final PlayerSearch search = new PlayerSearch()
                .setId(playerId);
        final ApiResponseModel<PlayerResponseModel> responseModel = footballApiClient.getPlayers(search);
        if (responseModel == null || responseModel.getResponse() == null || responseModel.getResponse().size() != 1) {
            throw new ValidationException("Player by id:" + playerId + " not found");
        }
        return saveFromApiPlayer(responseModel.getResponse().get(0));
    }

    private PlayerModel saveFromApiPlayer(PlayerResponseModel playerResponseModel) {
        PlayerModel playerModel = playerMapper.toModel(playerResponseModel);
        if (playerRepository.existsByApiId(playerModel.getApiId())) {
            return null;
        }
        DBPlayer player = playerMapper.toEntity(playerModel);

        DBPlayer dbPlayer = playerRepository.save(player);
        return new PlayerModel(dbPlayer);
    }
}
