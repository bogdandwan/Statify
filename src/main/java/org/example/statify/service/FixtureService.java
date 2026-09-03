package org.example.statify.service;

import org.example.statify.api.fixture.FixtureResponseModel;
import org.example.statify.model.FixtureModel;

public interface FixtureService {

  void importFixtures(Integer leagueId, Integer seasonYear);

  FixtureModel saveFromApiFixture(FixtureResponseModel responseModel);

  FixtureModel saveFixtureById(Integer fixtureId);
}
