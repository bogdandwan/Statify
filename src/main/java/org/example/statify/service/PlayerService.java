package org.example.statify.service;

import org.example.statify.model.PlayerModel;

public interface PlayerService {

    void importPlayer(Integer leagueId, Integer seasonYear);

    PlayerModel getPlayerByApiId(Integer playerId);

    PlayerModel savePlayerById(Integer playerId);

}
