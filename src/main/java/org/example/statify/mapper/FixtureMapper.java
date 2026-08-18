package org.example.statify.mapper;


import org.example.statify.dto.fixture.FixtureResponseModel;
import org.example.statify.entity.*;
import org.example.statify.model.FixtureModel;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class FixtureMapper {

    public FixtureModel toModel(FixtureResponseModel response) {

        FixtureModel model = new FixtureModel();

        model.setId(response.getFixture().getId());
        model.setReferee(response.getFixture().getReferee());
        model.setTimezone(response.getFixture().getTimezone());
        model.setDate(OffsetDateTime.parse(response.getFixture().getDate()));
        model.setTimestamp(response.getFixture().getTimestamp());
        model.setFirstPeriod(response.getFixture().getPeriods().getFirst());
        model.setSecondPeriod(response.getFixture().getPeriods().getSecond());
        model.setStatusLong(response.getFixture().getStatus().getLongName());
        model.setStatusShort(response.getFixture().getStatus().getShortName());
        model.setElapsed(response.getFixture().getStatus().getElapsed());
        model.setExtra(response.getFixture().getStatus().getExtra());
        model.setLeagueId(response.getLeague().getId());
        model.setSeasonYear(response.getLeague().getSeason());
        model.setHomeTeamId(response.getTeams().getHome().getId());
        model.setAwayTeamId(response.getTeams().getAway().getId());
        model.setVenueId(response.getFixture().getVenue().getId());
        model.setScore(response.getScore());

        return model;
    }

    public DBFixture toEntity(FixtureModel model, DBLeague league, DBSeason season, DBTeam homeTeam, DBTeam awayTeam, DBVenue venue) {

        DBFixture entity = new DBFixture();

        entity.setApiId(model.getId());
        entity.setReferee(model.getReferee());
        entity.setTimezone(model.getTimezone());
        entity.setDate(model.getDate());
        entity.setTimestamp(model.getTimestamp());
        entity.setFirstPeriod(model.getFirstPeriod());
        entity.setSecondPeriod(model.getSecondPeriod());
        entity.setStatusLong(model.getStatusLong());
        entity.setStatusShort(model.getStatusShort());
        entity.setElapsed(model.getElapsed());
        entity.setExtra(model.getExtra());

        entity.setLeague(league);
        entity.setSeason(season);
        entity.setHomeTeam(homeTeam);
        entity.setAwayTeam(awayTeam);
        entity.setVenue(venue);

        return entity;
    }
}