package org.example.statify.service;

import java.util.List;
import org.example.statify.entity.DBScore;
import org.example.statify.search.ScoreSearch;

public interface ScoreService {

  List<DBScore> findAll(ScoreSearch search);
}
