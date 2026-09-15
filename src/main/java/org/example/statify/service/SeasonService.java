package org.example.statify.service;

import java.util.List;
import org.example.statify.model.SeasonModel;
import org.example.statify.search.SeasonSearch;

public interface SeasonService {

  List<SeasonModel> findAll(SeasonSearch search);
}
