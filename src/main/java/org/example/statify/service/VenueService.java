package org.example.statify.service;

import java.util.List;
import org.example.statify.api.venue.VenueResponseModel;
import org.example.statify.model.VenueModel;
import org.example.statify.search.VenueSearch;

public interface VenueService {

  void importVenues(String country);

  VenueModel getVenueByApiId(Integer venueId);

  VenueModel saveFromApiVenue(VenueResponseModel venueResponseModel);

  VenueModel saveVenueById(Integer venueId);

  List<VenueModel> findAll(VenueSearch search);
}
