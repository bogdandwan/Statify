package org.example.statify.service;

import org.example.statify.api.venue.VenueResponseModel;
import org.example.statify.model.VenueModel;

public interface VenueService {

  void importVenues(String country);

  VenueModel getVenueByApiId(Integer venueId);

  VenueModel saveFromApiVenue(VenueResponseModel venueResponseModel);

  VenueModel saveVenueById(Integer venueId);
}
