package org.example.statify.mapper;

import lombok.RequiredArgsConstructor;
import org.example.statify.dto.SeasonResponseModel;
import org.example.statify.entity.DBSeason;
import org.example.statify.model.SeasonModel;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class SeasonMapper {

    private final CoverageMapper coverageMapper;


    public SeasonModel toModel(SeasonResponseModel response) {

        SeasonModel model = new SeasonModel();

        model.setYear(response.getYear());
        if (response.getStart() != null) {
            model.setStart(
                    LocalDate.parse(response.getStart())
            );
        }

        if (response.getEnd() != null) {
            model.setEnd(
                    LocalDate.parse(response.getEnd())
            );
        }
        model.setCurrent(response.getCurrent());

        if (response.getCoverage() != null) {
            model.setCoverage(coverageMapper.toModel(response.getCoverage()));
        }

        return model;
    }



    public DBSeason toEntity(SeasonModel model) {

        DBSeason entity = new DBSeason();

        entity.setYear(model.getYear());
        entity.setStart(model.getStart());
        entity.setEnd(model.getEnd());
        entity.setCurrent(model.getCurrent());

        if (model.getCoverage() != null) {
            entity.setCoverage(coverageMapper.toEntity(model.getCoverage()));
        }
        return entity;
    }
}
