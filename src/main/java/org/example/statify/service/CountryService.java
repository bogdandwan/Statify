package org.example.statify.service;

import org.example.statify.api.country.CountryResponseModel;
import org.example.statify.model.CountryModel;

public interface CountryService {

    void importCountries(String name);

    CountryModel saveFromApiCountry(CountryModel countryModel);

    CountryModel saveCountryByName(String name);
}
