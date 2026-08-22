package org.example.statify.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.statify.entity.DBLeague;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LeagueModel {

    private Long id;
    private Integer apiId;
    private String name;
    private String type;
    private String logo;
    private CountryModel country;
    private List<SeasonModel> seasons;


    public LeagueModel(DBLeague dbLeague) {
        this.id = dbLeague.getId();
        this.apiId = dbLeague.getApiId();
        this.name = dbLeague.getName();
        this.type = dbLeague.getType();
        this.logo = dbLeague.getLogo();
        if  (dbLeague.getCountry() != null) {
            this.country = new CountryModel(dbLeague.getCountry());
        }
        if  (dbLeague.getSeasons() != null) {
            this.seasons = dbLeague.getSeasons()
                    .stream()
                    .map(SeasonModel::new)
                    .toList();
        }
    }
}
