package org.example.statify.mapper;

import org.example.statify.dto.VenueResponseModel;
import org.example.statify.entity.DBVenue;
import org.example.statify.model.VenueModel;
import org.springframework.stereotype.Component;

@Component
public class VenueMapper {

    public VenueModel toModel(VenueResponseModel response) {

        VenueModel model = new VenueModel();

        model.setId(response.getId());
        model.setName(response.getName());
        model.setAddress(response.getAddress());
        model.setCity(response.getCity());
        model.setCountry(response.getCountry());
        model.setCapacity(response.getCapacity());
        model.setSurface(response.getSurface());
        model.setImage(response.getImage());

        return model;
    }


    public DBVenue toEntity(VenueModel model) {

        DBVenue entity = new DBVenue();

        entity.setApiId(model.getId());
        entity.setName(model.getName());
        entity.setAddress(model.getAddress());
        entity.setCity(model.getCity());
        entity.setCountry(model.getCountry());
        entity.setCapacity(model.getCapacity());
        entity.setSurface(model.getSurface());
        entity.setImage(model.getImage());

        return entity;
    }
}
