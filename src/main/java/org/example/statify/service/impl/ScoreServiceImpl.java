package org.example.statify.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.statify.entity.DBScore;
import org.example.statify.model.ScoreModel;
import org.example.statify.repository.ScoreRepository;
import org.example.statify.search.ScoreSearch;
import org.example.statify.search.spec.ScoreSpec;
import org.example.statify.service.ScoreService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScoreServiceImpl implements ScoreService {

  private final ScoreRepository scoreRepository;

  @Override
  public List<ScoreModel> findAll(ScoreSearch search) {

    final List<DBScore> dbScores = scoreRepository.findAll(new ScoreSpec(search));

    return dbScores.stream().map(ScoreModel::new).collect(Collectors.toList());
  }
}
