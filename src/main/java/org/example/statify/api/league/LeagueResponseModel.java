package org.example.statify.api.league;

import lombok.Getter;
import lombok.Setter;
import org.example.statify.api.season.SeasonResponseModel;
import org.example.statify.model.CountryModel;
import org.example.statify.model.LeagueModel;

import java.util.List;

@Getter
@Setter
public class LeagueResponseModel {

    private LeagueModel league;
    private CountryModel country;
    private List<SeasonResponseModel> seasons;
}
