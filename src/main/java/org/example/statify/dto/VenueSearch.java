package org.example.statify.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain=true)
public class VenueSearch {
    private Integer id;
    private String name;
    private String city;
    private String country;
    private String fullText;
}
