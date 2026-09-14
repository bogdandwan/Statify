package org.example.statify.service;

import java.util.List;
import org.example.statify.api.fixture.FixtureResponseModel;
import org.example.statify.entity.DBFixture;
import org.example.statify.model.FixtureModel;
import org.example.statify.search.FixtureSearch;

public interface FixtureService {

  void importFixtures(Integer leagueId, Integer seasonYear);

  FixtureModel saveFromApiFixture(FixtureResponseModel responseModel);

  FixtureModel saveFixtureById(Integer fixtureId);

  List<DBFixture> findAll(FixtureSearch search);

  DBFixture findById(Long id);

  DBFixture findByApiId(Integer apiId);

  void syncUpcomingFixtures();
}
