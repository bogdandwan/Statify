package org.example.statify.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VenueModel {

    private Long id;
    private Long apiId;
    private String name;
    private String address;
    private String city;
    private String country;
    private Integer capacity;
    private String surface;
    private String image;
}
