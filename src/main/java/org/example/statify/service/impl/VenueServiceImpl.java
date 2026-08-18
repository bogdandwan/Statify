package org.example.statify.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.statify.client.FootballApiClient;
import org.example.statify.dto.DTOResponseModel;
import org.example.statify.dto.VenueResponseModel;
import org.example.statify.entity.DBVenue;
import org.example.statify.mapper.VenueMapper;
import org.example.statify.model.VenueModel;
import org.example.statify.repository.VenueRepository;
import org.example.statify.service.VenueService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class VenueServiceImpl implements VenueService {

    private final FootballApiClient footballApiClient;
    private final VenueMapper venueMapper;
    private final VenueRepository venueRepository;

    @Override
    public void importVenues(String country) {

        DTOResponseModel<VenueResponseModel> response = footballApiClient.getVenuesByCountry(country);

        for (VenueResponseModel responseModel : response.getResponse()) {

            VenueModel venueModel = venueMapper.toModel(responseModel);
            if (venueRepository.existsByApiId(venueModel.getApiId())) {
                continue;
            }

            DBVenue venue = venueMapper.toEntity(venueModel);

            venueRepository.save(venue);
        }
    }
}

