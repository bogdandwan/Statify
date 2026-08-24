package org.example.statify.mapper;

import org.example.statify.api.score.ScoreDetailResponseModel;
import org.example.statify.entity.DBFixture;
import org.example.statify.entity.DBScore;
import org.example.statify.entity.enums.ScoreType;
import org.example.statify.model.ScoreModel;
import org.springframework.stereotype.Component;

@Component
public class ScoreMapper {

    public ScoreModel toModel(ScoreDetailResponseModel response, ScoreType type) {

        ScoreModel model = new ScoreModel();

        model.setHome(response.getHome());
        model.setAway(response.getAway());
        model.setType(type);

        return model;
    }

    public DBScore toEntity(ScoreModel model, DBFixture fixture) {

        DBScore entity = new DBScore();

        entity.setHome(model.getHome());
        entity.setAway(model.getAway());
        entity.setType(model.getType());
        entity.setFixture(fixture);

        return entity;
    }
}
