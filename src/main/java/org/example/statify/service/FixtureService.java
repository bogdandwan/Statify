package org.example.statify.service;

import java.util.List;
import org.example.statify.api.fixture.FixtureResponseModel;
import org.example.statify.model.FixtureModel;
import org.example.statify.search.FixtureSearch;

public interface FixtureService {

  void importFixtures(Integer leagueId, Integer seasonYear);

  FixtureModel saveFromApiFixture(FixtureResponseModel responseModel);

  FixtureModel saveFixtureById(Integer fixtureId);

  List<FixtureModel> findAll(FixtureSearch search);

  FixtureModel findById(Long id);

  FixtureModel findByApiId(Integer apiId);

  void syncUpcomingFixtures();

  void importAllFixtures(Integer leagueId, Integer seasonYear);
}
