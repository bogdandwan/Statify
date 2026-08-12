package org.example.statify.mapper;

import org.example.statify.entity.DBCountry;
import org.example.statify.model.CountryModel;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper {

    public DBCountry toEntity(CountryModel countryModel) {

        DBCountry entity = new DBCountry();

        entity.setName(countryModel.getName());
        entity.setCode(countryModel.getCode());
        entity.setFlag(countryModel.getFlag());

        return entity;
    }
}
