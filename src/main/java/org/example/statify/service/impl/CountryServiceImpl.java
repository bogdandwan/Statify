package org.example.statify.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.statify.api.ApiResponseModel;
import org.example.statify.api.CountrySearch;
import org.example.statify.client.impl.FootballApiClientImpl;
import org.example.statify.entity.DBCountry;
import org.example.statify.entity.exceptions.ValidationException;
import org.example.statify.mapper.CountryMapper;
import org.example.statify.model.CountryModel;
import org.example.statify.repository.CountryRepository;
import org.example.statify.service.CountryService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

  private final FootballApiClientImpl footballApiClient;
  private final CountryMapper countryMapper;
  private final CountryRepository countryRepository;

  @Transactional
  public void importCountries(String name) {

    CountrySearch search = new CountrySearch().setName(name);
    ApiResponseModel<CountryModel> response = footballApiClient.getCountries(search);

    /*for (CountryModel countryModel : response.getResponse()) {
      saveFromApiCountry(countryModel);
    }*/
    response.getResponse().forEach(this::saveFromApiCountry);
  }

  public CountryModel saveCountryByName(String name) {

    CountrySearch search = new CountrySearch().setName(name);
    ApiResponseModel<CountryModel> response = footballApiClient.getCountries(search);

    if (response == null || response.getResponse() == null || response.getResponse().size() != 1) {
      throw new ValidationException("Country: " + name + " not found");
    }
    return saveFromApiCountry(response.getResponse().get(0));
  }

  public CountryModel saveFromApiCountry(CountryModel countryModel) {

    if (countryRepository.existsByName(countryModel.getName())) {
      return null;
    }

    DBCountry country = countryMapper.toEntity(countryModel);
    DBCountry savedCountry = countryRepository.save(country);

    return new CountryModel(savedCountry);
  }
}
