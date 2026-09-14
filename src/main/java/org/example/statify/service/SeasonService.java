package org.example.statify.service;

import java.util.List;
import org.example.statify.entity.DBSeason;
import org.example.statify.search.SeasonSearch;

public interface SeasonService {

  List<DBSeason> findAll(SeasonSearch search);
}
