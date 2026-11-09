package org.example.statify.service.impl;

import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.statify.api.ApiResponseModel;
import org.example.statify.api.ApiVenueSearch;
import org.example.statify.api.venue.VenueResponseModel;
import org.example.statify.client.FootballApiClientService;
import org.example.statify.entity.DBVenue;
import org.example.statify.entity.exceptions.ValidationException;
import org.example.statify.mapper.VenueMapper;
import org.example.statify.model.CountryModel;
import org.example.statify.model.VenueModel;
import org.example.statify.repository.VenueRepository;
import org.example.statify.search.CountrySearch;
import org.example.statify.search.VenueSearch;
import org.example.statify.search.spec.VenueSpec;
import org.example.statify.service.CountryService;
import org.example.statify.service.VenueService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class VenueServiceImpl implements VenueService {

  private final FootballApiClientService footballApiClient;
  private final VenueMapper venueMapper;
  private final VenueRepository venueRepository;
  private final CountryService countryService;

  @Override
  public void importVenues() {
    final List<CountryModel> countries = countryService.findAll(new CountrySearch());
    countries.forEach(
        country -> {
          final ApiVenueSearch search = new ApiVenueSearch().setCountry(country.getName());
          ApiResponseModel<VenueResponseModel> response = footballApiClient.getVenues(search);

          /*for (VenueResponseModel responseModel : response.getResponse()) {
            saveFromApiVenue(responseModel);
          }*/
          response.getResponse().forEach(this::saveFromApiVenue);
        });
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
    final ApiVenueSearch search = new ApiVenueSearch().setId(venueId);
    final ApiResponseModel<VenueResponseModel> responseModel = footballApiClient.getVenues(search);
    if (responseModel == null
        || responseModel.getResponse() == null
        || responseModel.getResponse().size() != 1) {
      throw new ValidationException("Venue by id:" + venueId + " not found");
    }
    return saveFromApiVenue(responseModel.getResponse().get(0));
  }

  @Override
  public List<VenueModel> findAll(VenueSearch search) {

    final List<DBVenue> dbVenues = venueRepository.findAll(new VenueSpec(search));

    return dbVenues.stream().map(VenueModel::new).collect(Collectors.toList());
  }

  public void checkApiVenues() {

    List<CountryModel> countries = countryService.findAll(new CountrySearch());

    Set<Integer> uniqueVenueIds = new HashSet<>();

    for (CountryModel country : countries) {

      ApiVenueSearch search = new ApiVenueSearch().setCountry(country.getName());

      ApiResponseModel<VenueResponseModel> response = footballApiClient.getVenues(search);

      if (response == null || response.getResponse() == null) {
        log.warn("Empty venue response | country = {}", country.getName());
        continue;
      }

      List<VenueResponseModel> venues = response.getResponse();

      venues.forEach(
          venue -> {
            if (venue.getId() != null) {
              uniqueVenueIds.add(venue.getId().intValue());
            }
          });

      log.info(
          "Venue check | country = {} | results = {} | unique total = {}",
          country.getName(),
          venues.size(),
          uniqueVenueIds.size());
    }

    log.info(
        "API VENUE CHECK FINISHED | countries = {} | unique venues = {}",
        countries.size(),
        uniqueVenueIds.size());
  }

  @Override
  public DBVenue findByApiId(Integer venueId) {
    DBVenue dbVenue = venueRepository.findByApiId(venueId);
    if (dbVenue == null) {
      throw new ValidationException("Venue by id:" + venueId + " not found");
    }
    return dbVenue;
  }
}
