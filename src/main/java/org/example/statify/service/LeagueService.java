package org.example.statify.service;

import java.util.List;
import org.example.statify.entity.DBLeague;
import org.example.statify.model.LeagueModel;
import org.example.statify.search.LeagueSearch;

public interface LeagueService {

  void importLeagues();

  LeagueModel saveLeagueById(Integer leagueId);

  LeagueModel getLeagueByApiId(Integer leagueId);

  List<DBLeague> findAll(LeagueSearch search);

  DBLeague findById(Long id);

  DBLeague findByApiId(Integer apiId);
}
