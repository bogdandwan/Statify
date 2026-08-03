package org.example.statify.mapper;

import org.example.statify.dto.SeasonsDto;
import org.example.statify.entity.DBSeasons;
import org.example.statify.model.SeasonsModel;
import org.springframework.stereotype.Component;

@Component
public class SeasonsMapper {


    public SeasonsModel toModel(SeasonsDto dto) {

        SeasonsModel model = new SeasonsModel();


        model.setYear(dto.getYear());

        model.setStart(dto.getStart());

        model.setEnd(dto.getEnd());

        model.setCurrent(dto.getCurrent());


        return model;
    }



    public DBSeasons toEntity(SeasonsModel model) {


        DBSeasons entity = new DBSeasons();


        entity.setYear(model.getYear());

        entity.setStart(model.getStart());

        entity.setEnd(model.getEnd());

        entity.setCurrent(model.getCurrent());


        return entity;
    }
}
