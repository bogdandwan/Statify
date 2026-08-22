package org.example.statify.dto.venue;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VenueResponseModel {

    private Long id;
    private String name;
    private String address;
    private String city;
    private String country;
    private Integer capacity;
    private String surface;
    private String image;
}
