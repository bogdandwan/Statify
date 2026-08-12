package org.example.statify.mapper;

import org.example.statify.dto.FixturesResponseModel;
import org.example.statify.entity.DBFixture;
import org.example.statify.model.FixtureModel;
import org.springframework.stereotype.Component;

@Component
public class FixtureMapper {

    public FixtureModel toModel(FixturesResponseModel response) {

        FixtureModel model = new FixtureModel();

        model.setEvents(response.getEvents());
        model.setLineups(response.getLineups());
        model.setStatisticsFixtures(response.getStatistics_fixtures());
        model.setStatisticsPlayers(response.getStatistics_players());

        return model;
    }

    public DBFixture toEntity(FixtureModel model) {

        DBFixture entity = new DBFixture();

        entity.setEvents(model.getEvents());
        entity.setLineups(model.getLineups());
        entity.setStatisticsFixtures(model.getStatisticsFixtures());
        entity.setStatisticsPlayers(model.getStatisticsPlayers());

        return entity;
    }
}
