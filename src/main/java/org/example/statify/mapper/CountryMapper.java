package org.example.statify.mapper;

import org.example.statify.dto.CountryDto;
import org.example.statify.entity.DBCountry;
import org.example.statify.model.CountryModel;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper {


    public CountryModel toModel(CountryDto dto) {

        CountryModel model = new CountryModel();

        model.setName(dto.getName());

        model.setCode(dto.getCode());

        model.setFlag(dto.getFlag());


        return model;
    }



    public DBCountry toEntity(CountryModel model) {

        DBCountry entity = new DBCountry();


        entity.setName(model.getName());

        entity.setCode(model.getCode());

        entity.setFlag(model.getFlag());


        return entity;
    }
}
