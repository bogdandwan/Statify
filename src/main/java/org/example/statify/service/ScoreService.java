package org.example.statify.service;

import java.util.List;
import org.example.statify.model.ScoreModel;
import org.example.statify.search.ScoreSearch;

public interface ScoreService {

  List<ScoreModel> findAll(ScoreSearch search);
}
