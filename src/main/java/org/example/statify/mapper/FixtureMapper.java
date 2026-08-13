package org.example.statify.mapper;

import org.example.statify.dto.fixtures.FixtureResponseModel;
import org.example.statify.entity.DBFixture;
import org.example.statify.model.FixtureModel;
import org.springframework.stereotype.Component;

@Component
public class FixtureMapper {

    public FixtureModel toModel(FixtureResponseModel response) {

        FixtureModel model = new FixtureModel();

        model.setApiId(response.getFixture().getId());
        model.setReferee(response.getFixture().getReferee());
        model.setDate(response.getFixture().getDate());

        if (response.getFixture().getStatus() != null) {

            model.setStatusLong(response.getFixture().getStatus().getLongStatus());
            model.setStatusShort(response.getFixture().getStatus().getShortStatus());
            model.setElapsed(response.getFixture().getStatus().getElapsed());
            model.setExtra(response.getFixture().getStatus().getExtra());
        }

        if (response.getLeague() != null) {

            model.setLeagueId(response.getLeague().getId());
            model.setSeason(response.getLeague().getSeason());
        }

        if (response.getTeams() != null) {

            if (response.getTeams().getHome() != null) {
                model.setHomeTeamId(response.getTeams().getHome().getId());
            }
            if (response.getTeams().getAway() != null) {
                model.setAwayTeamId(response.getTeams().getAway().getId()
                );
            }
        }

        if (response.getGoals() != null) {

            model.setHomeGoals(response.getGoals().getHome());
            model.setAwayGoals(response.getGoals().getAway());
        }

        if (response.getScore() != null) {

            if (response.getScore().getHalftime() != null) {

                model.setHalftimeHome(
                        response.getScore()
                                .getHalftime()
                                .getHome()
                );

                model.setHalftimeAway(
                        response.getScore()
                                .getHalftime()
                                .getAway()
                );
            }

            if (response.getScore().getFulltime() != null) {

                model.setFulltimeHome(
                        response.getScore()
                                .getFulltime()
                                .getHome()
                );

                model.setFulltimeAway(
                        response.getScore()
                                .getFulltime()
                                .getAway()
                );
            }

            if (response.getScore().getExtratime() != null) {

                model.setExtratimeHome(
                        response.getScore()
                                .getExtratime()
                                .getHome()
                );

                model.setExtratimeAway(
                        response.getScore()
                                .getExtratime()
                                .getAway()
                );
            }

            if (response.getScore().getPenalty() != null) {

                model.setPenaltyHome(
                        response.getScore()
                                .getPenalty()
                                .getHome()
                );

                model.setPenaltyAway(
                        response.getScore()
                                .getPenalty()
                                .getAway()
                );
            }
        }

        return model;
    }

    public DBFixture toEntity(FixtureModel model) {

        DBFixture entity = new DBFixture();

        entity.setApiId(model.getApiId());
        entity.setReferee(model.getReferee());
        entity.setDate(model.getDate());

        entity.setStatusLong(model.getStatusLong());
        entity.setStatusShort(model.getStatusShort());
        entity.setElapsed(model.getElapsed());
        entity.setExtra(model.getExtra());

        entity.setHomeGoals(model.getHomeGoals());
        entity.setAwayGoals(model.getAwayGoals());

        entity.setHalftimeHome(model.getHalftimeHome());
        entity.setHalftimeAway(model.getHalftimeAway());

        entity.setFulltimeHome(model.getFulltimeHome());
        entity.setFulltimeAway(model.getFulltimeAway());

        entity.setExtratimeHome(model.getExtratimeHome());
        entity.setExtratimeAway(model.getExtratimeAway());

        entity.setPenaltyHome(model.getPenaltyHome());
        entity.setPenaltyAway(model.getPenaltyAway());

        return entity;
    }
}
