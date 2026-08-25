package org.example.statify.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.example.statify.entity.DBVenue;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain=true)
public class VenueModel {

    private Long id;
    private Integer apiId;
    private String name;
    private String address;
    private String city;
    private String country;
    private Integer capacity;
    private String surface;
    private String image;

    public VenueModel(DBVenue dbVenue) {
        this.id = dbVenue.getId();
        this.apiId = dbVenue.getApiId();
        this.name = dbVenue.getName();
        this.address = dbVenue.getAddress();
        this.city = dbVenue.getCity();
        this.country = dbVenue.getCountry();
        this.capacity = dbVenue.getCapacity();
        this.surface = dbVenue.getSurface();
        this.image = dbVenue.getImage();
    }
}
