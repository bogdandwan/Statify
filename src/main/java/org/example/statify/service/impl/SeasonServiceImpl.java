package org.example.statify.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.statify.entity.DBSeason;
import org.example.statify.repository.SeasonRepository;
import org.example.statify.search.SeasonSearch;
import org.example.statify.search.spec.SeasonSpec;
import org.example.statify.service.SeasonService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeasonServiceImpl implements SeasonService {

  private final SeasonRepository seasonRepository;

  @Override
  public List<DBSeason> findAll(SeasonSearch search) {

    return seasonRepository.findAll(new SeasonSpec(search));
  }
}
