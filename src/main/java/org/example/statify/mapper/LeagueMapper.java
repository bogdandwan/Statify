package org.example.statify.mapper;

import org.example.statify.dto.LeagueResponseDto;
import org.example.statify.entity.DBLeague;
import org.example.statify.model.LeagueModel;
import org.springframework.stereotype.Component;

@Component
public class LeagueMapper {

    private final CountryMapper countryMapper;
    private final SeasonsMapper seasonMapper;


    public LeagueMapper(
            CountryMapper countryMapper,
            SeasonsMapper seasonMapper
    ) {
        this.countryMapper = countryMapper;
        this.seasonMapper = seasonMapper;
    }


    public LeagueModel toModel(LeagueResponseDto dto) {

        LeagueModel model = new LeagueModel();

        model.setId(dto.getLeague().getId());
        model.setName(dto.getLeague().getName());
        model.setType(dto.getLeague().getType());
        model.setLogo(dto.getLeague().getLogo());


        if (dto.getCountry() != null) {
            model.setCountry(
                    countryMapper.toModel(dto.getCountry())
            );
        }


        if (dto.getSeasons() != null) {

            model.setSeasons(
                    dto.getSeasons()
                            .stream()
                            .map(seasonMapper::toModel)
                            .toList()
            );
        }


        return model;
    }


    public DBLeague toEntity(LeagueModel model) {

        DBLeague entity = new DBLeague();


        entity.setId(model.getId());

        entity.setName(model.getName());

        entity.setType(model.getType());

        entity.setLogo(model.getLogo());


        if(model.getCountry() != null) {

            entity.setCountry(
                    countryMapper.toEntity(model.getCountry())
            );
        }


        if(model.getSeasons() != null) {

            entity.setSeasons(
                    model.getSeasons()
                            .stream()
                            .map(seasonMapper::toEntity)
                            .toList()
            );
        }


        return entity;
    }
}
