package org.example.statify.service;

import java.util.List;
import org.example.statify.entity.DBPlayer;
import org.example.statify.model.PlayerModel;
import org.example.statify.search.PlayerSearch;

public interface PlayerService {

  void importPlayer(Integer leagueId, Integer seasonYear);

  PlayerModel getPlayerByApiId(Integer playerId);

  PlayerModel savePlayerById(Integer playerId);

  List<DBPlayer> findAll(PlayerSearch search);
}
