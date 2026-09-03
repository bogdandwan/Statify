package org.example.statify.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.statify.entity.DBTeam;

@Getter
@Setter
@NoArgsConstructor
public class TeamModel {

  private Long id;
  private Integer apiId;
  private String name;
  private String code;
  private String country;
  private Integer founded;
  private Boolean national;
  private String logo;

  public TeamModel(DBTeam dbTeam) {
    this.id = dbTeam.getId();
    this.apiId = dbTeam.getApiId();
    this.name = dbTeam.getName();
    this.code = dbTeam.getCode();
    this.country = dbTeam.getCountry();
    this.founded = dbTeam.getFounded();
    this.national = dbTeam.getNational();
    this.logo = dbTeam.getLogo();
  }
}
