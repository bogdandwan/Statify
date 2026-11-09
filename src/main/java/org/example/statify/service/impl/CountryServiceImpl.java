package org.example.statify.service.impl;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.statify.api.ApiCountrySearch;
import org.example.statify.api.ApiResponseModel;
import org.example.statify.client.impl.FootballApiClientImpl;
import org.example.statify.entity.DBCountry;
import org.example.statify.entity.exceptions.ValidationException;
import org.example.statify.mapper.CountryMapper;
import org.example.statify.model.CountryModel;
import org.example.statify.repository.CountryRepository;
import org.example.statify.search.CountrySearch;
import org.example.statify.search.spec.CountrySpec;
import org.example.statify.service.CountryService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CountryServiceImpl implements CountryService {

  private final FootballApiClientImpl footballApiClient;
  private final CountryMapper countryMapper;
  private final CountryRepository countryRepository;

  @Transactional
  public void importCountries() {

    ApiCountrySearch search = new ApiCountrySearch();

    ApiResponseModel<CountryModel> response = footballApiClient.getCountries(search);

    if (response == null || response.getResponse() == null) {
      throw new IllegalStateException("Failed to import countries from API");
    }

    response.getResponse().forEach(this::saveFromApiCountry);

    log.info("Countries import completed");
  }

  public CountryModel saveCountryByName(String name) {

    ApiCountrySearch search = new ApiCountrySearch().setName(name);
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

  @Override
  public List<CountryModel> findAll(CountrySearch search) {

    List<DBCountry> dbCountries = countryRepository.findAll(new CountrySpec(search));

    return dbCountries.stream().map(CountryModel::new).collect(Collectors.toList());
  }

  @Override
  public DBCountry findByName(String name) {
    DBCountry country = countryRepository.findByName(name);
    if (country == null) throw new ValidationException("Country: " + name + " not found");
    return country;
  }
}
