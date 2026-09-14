package org.example.statify.api.fixture;

import lombok.Getter;
import lombok.Setter;
import org.example.statify.api.venue.VenueResponseModel;

@Getter
@Setter
public class FixtureDetailResponseModel {

  private Integer id;
  private String referee;
  private String timezone;
  private String date;
  private Long timestamp;
  private PeriodsResponseModel periods;
  private VenueResponseModel venue;
  private StatusResponseModel status;
}
