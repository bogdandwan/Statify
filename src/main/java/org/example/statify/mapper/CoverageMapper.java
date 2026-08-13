package org.example.statify.mapper;

import lombok.RequiredArgsConstructor;
import org.example.statify.dto.CoverageResponseModel;
import org.example.statify.entity.DBCountry;
import org.example.statify.entity.DBCoverage;
import org.example.statify.entity.DBFixture;
import org.example.statify.model.CoverageModel;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CoverageMapper {


    public CoverageModel toModel(CoverageResponseModel response) {

        CoverageModel model = new CoverageModel();

        model.setStandings(response.getStandings());
        model.setPlayers(response.getPlayers());
        model.setTopScorers(response.getTop_scorers());
        model.setTopAssists(response.getTop_assists());
        model.setTopCards(response.getTop_cards());
        model.setInjuries(response.getInjuries());
        model.setPredictions(response.getPredictions());
        model.setOdds(response.getOdds());


        return model;
    }

    public DBCoverage toEntity(CoverageModel model) {
        DBCoverage entity = new DBCoverage();

        entity.setStandings(model.getStandings());
        entity.setPlayers(model.getPlayers());
        entity.setTopScorers(model.getTopScorers());
        entity.setTopAssists(model.getTopAssists());
        entity.setTopCards(model.getTopCards());
        entity.setInjuries(model.getInjuries());
        entity.setPredictions(model.getPredictions());
        entity.setOdds(model.getOdds());

        return entity;
    }

}
