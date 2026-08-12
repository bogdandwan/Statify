package org.example.statify.mapper;

import lombok.RequiredArgsConstructor;
import org.example.statify.dto.LeagueResponseModel;
import org.example.statify.entity.DBLeague;
import org.example.statify.model.LeagueModel;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LeagueMapper {

    private final CountryMapper countryMapper;
    private final SeasonMapper seasonMapper;


    public LeagueModel toModel(LeagueResponseModel response) {

        LeagueModel model = new LeagueModel();

        model.setId(response.getLeague().getId());
        model.setName(response.getLeague().getName());
        model.setType(response.getLeague().getType());
        model.setLogo(response.getLeague().getLogo());


        if (response.getCountry() != null) {
            model.setCountry(response.getCountry());
        }

        if (response.getSeasons() != null) {

            model.setSeasons(
                    response.getSeasons()
                            .stream()
                            .map(seasonMapper::toModel)
                            .toList()
            );
        }

        return model;
    }


    public DBLeague toEntity(LeagueModel model) {

        DBLeague entity = new DBLeague();

        entity.setApiId(model.getId());
        entity.setName(model.getName());
        entity.setType(model.getType());
        entity.setLogo(model.getLogo());

        return entity;
    }
}
