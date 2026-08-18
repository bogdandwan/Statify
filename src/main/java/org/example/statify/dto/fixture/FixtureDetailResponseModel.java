package org.example.statify.dto.fixture;

import lombok.Getter;
import lombok.Setter;
import org.example.statify.dto.VenueResponseModel;

@Getter
@Setter
public class FixtureDetailResponseModel {

    private Long id;
    private String referee;
    private String timezone;
    private String date;
    private Long timestamp;
    private PeriodsResponseModel periods;
    private VenueResponseModel venue;
    private StatusResponseModel status;
}
