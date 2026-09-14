package org.example.statify.search;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class VenueSearch {

  private Long id;
  private Long apiId;
  private String name;
  private String address;
  private String city;
  private String country;
  private String surface;
  private Integer capacity;
  private Integer capacityFrom;
  private Integer capacityTo;
}
