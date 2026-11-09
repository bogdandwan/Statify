package org.example.statify.service;

import java.util.List;
import org.example.statify.entity.DBCountry;
import org.example.statify.model.CountryModel;
import org.example.statify.search.CountrySearch;

public interface CountryService {

  void importCountries();

  CountryModel saveFromApiCountry(CountryModel countryModel);

  CountryModel saveCountryByName(String name);

  List<CountryModel> findAll(CountrySearch search);

  DBCountry findByName(String name);
}
