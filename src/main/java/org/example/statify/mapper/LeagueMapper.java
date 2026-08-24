package org.example.statify.mapper;

import lombok.RequiredArgsConstructor;
import org.example.statify.api.league.LeagueResponseModel;
import org.example.statify.entity.DBLeague;
import org.example.statify.entity.DBSeason;
import org.example.statify.model.LeagueModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LeagueMapper {

    private final SeasonMapper seasonMapper;

    public LeagueModel toModel(LeagueResponseModel response) {

        LeagueModel model = new LeagueModel();

        model.setApiId(response.getLeague().getId().intValue());
        model.setName(response.getLeague().getName());
        model.setType(response.getLeague().getType());
        model.setLogo(response.getLeague().getLogo());

        if (response.getCountry() != null) {
            model.setCountry(response.getCountry());
        }

        if (response.getSeasons() != null) {
            model.setSeasons(response.getSeasons()
                            .stream()
                            .map(seasonMapper::toModel)
                            .toList());
        }

        return model;
    }


    public DBLeague toEntity(LeagueModel model) {

        DBLeague entity = new DBLeague();

        entity.setApiId(model.getApiId());
        entity.setName(model.getName());
        entity.setType(model.getType());
        entity.setLogo(model.getLogo());

        if (model.getSeasons() != null) {

            List<DBSeason> seasons = model.getSeasons()
                            .stream()
                            .map(seasonMapper::toEntity)
                            .toList();

            seasons.forEach(season -> season.setLeague(entity));

            entity.setSeasons(seasons);
        }

        return entity;
    }
}
