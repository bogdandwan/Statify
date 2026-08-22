package org.example.statify.service;

import org.example.statify.model.VenueModel;

public interface VenueService {

    void importVenues(String country);

    VenueModel getVenueByApiId(Integer venueId);
}
