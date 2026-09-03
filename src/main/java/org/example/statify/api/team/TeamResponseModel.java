package org.example.statify.api.team;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamResponseModel {

  private Long id;
  private String name;
  private String code;
  private String country;
  private Integer founded;
  private Boolean national;
  private String logo;
}
