package org.example.statify.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.statify.client.FootballApiClient;
import org.example.statify.dto.ApiResponseModel;
import org.example.statify.dto.LeagueResponseModel;
import org.example.statify.entity.DBCountry;
import org.example.statify.entity.DBLeague;
import org.example.statify.mapper.CountryMapper;
import org.example.statify.mapper.LeagueMapper;
import org.example.statify.model.CountryModel;
import org.example.statify.model.LeagueModel;
import org.example.statify.repository.CountryRepository;
import org.example.statify.repository.LeagueRepository;
import org.example.statify.service.LeagueService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeagueServiceImpl implements LeagueService {


    private final FootballApiClient footballApiClient;
    private final LeagueMapper leagueMapper;
    private final LeagueRepository leagueRepository;
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;


    @Transactional
    public void importLeagues() {

        ApiResponseModel<LeagueResponseModel> response =
                footballApiClient.getLeagues();

        for (LeagueResponseModel responseModel : response.getResponse()) {

            LeagueModel leagueModel = leagueMapper.toModel(responseModel);
            DBLeague league = leagueMapper.toEntity(leagueModel);

            if (responseModel.getCountry() != null) {

                String countryName = responseModel.getCountry().getName();

                DBCountry country =
                        countryRepository.findByName(countryName)
                                .orElseThrow(() ->
                                        new IllegalStateException("Country not found: " + countryName));
                league.setCountry(country);
            }

            leagueRepository.save(league);
        }
    }
}
