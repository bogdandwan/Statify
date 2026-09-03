package org.example.statify.api.league;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.example.statify.api.season.SeasonResponseModel;
import org.example.statify.model.CountryModel;
import org.example.statify.model.LeagueModel;

@Getter
@Setter
public class LeagueResponseModel {

  private LeagueModel league;
  private CountryModel country;
  private List<SeasonResponseModel> seasons;
}
