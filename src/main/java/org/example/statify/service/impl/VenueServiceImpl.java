package org.example.statify.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.statify.api.ApiResponseModel;
import org.example.statify.api.VenueSearch;
import org.example.statify.api.venue.VenueResponseModel;
import org.example.statify.client.FootballApiClientService;
import org.example.statify.entity.DBVenue;
import org.example.statify.entity.exceptions.ValidationException;
import org.example.statify.mapper.VenueMapper;
import org.example.statify.model.VenueModel;
import org.example.statify.repository.VenueRepository;
import org.example.statify.service.VenueService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VenueServiceImpl implements VenueService {

  private final FootballApiClientService footballApiClient;
  private final VenueMapper venueMapper;
  private final VenueRepository venueRepository;

  @Override
  public void importVenues(String country) {

    final VenueSearch search = new VenueSearch().setCountry(country);
    ApiResponseModel<VenueResponseModel> response = footballApiClient.getVenues(search);

    /*for (VenueResponseModel responseModel : response.getResponse()) {
      saveFromApiVenue(responseModel);
    }*/
    response.getResponse().forEach(this::saveFromApiVenue);
  }

  public VenueModel saveFromApiVenue(VenueResponseModel venueResponseModel) {
    VenueModel venueModel = venueMapper.toModel(venueResponseModel);
    if (venueRepository.existsByApiId(venueModel.getApiId())) {
      return null;
    }
    DBVenue venue = venueMapper.toEntity(venueModel);

    DBVenue dbVenue = venueRepository.save(venue);
    return new VenueModel(dbVenue);
  }

  @Override
  @Transactional
  public VenueModel getVenueByApiId(Integer venueId) {
    final DBVenue dbVenue = venueRepository.findByApiId(venueId);
    if (dbVenue == null) {
      return saveVenueById(venueId);
    }
    return new VenueModel(dbVenue);
  }

  public VenueModel saveVenueById(Integer venueId) {
    final VenueSearch search = new VenueSearch().setId(venueId);
    final ApiResponseModel<VenueResponseModel> responseModel = footballApiClient.getVenues(search);
    if (responseModel == null
        || responseModel.getResponse() == null
        || responseModel.getResponse().size() != 1) {
      throw new ValidationException("Venue by id:" + venueId + " not found");
    }
    return saveFromApiVenue(responseModel.getResponse().get(0));
  }
}
