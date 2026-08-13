package org.example.statify.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.statify.client.FootballApiClient;
import org.example.statify.dto.DTOResponseModel;
import org.example.statify.entity.DBCountry;
import org.example.statify.mapper.CountryMapper;
import org.example.statify.model.CountryModel;
import org.example.statify.repository.CountryRepository;
import org.example.statify.service.CountryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final FootballApiClient client;
    private final CountryMapper countryMapper;
    private final CountryRepository countryRepository;

    @Transactional
    public void importCountries() {

        DTOResponseModel<CountryModel> response = client.getCountries();

        List<DBCountry> countries =
                response.getResponse()
                        .stream()
                        .map(countryMapper::toEntity)
                        .toList();

        countryRepository.saveAll(countries);
    }
}
