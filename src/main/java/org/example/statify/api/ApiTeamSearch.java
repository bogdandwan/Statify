package org.example.statify.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class ApiTeamSearch {

  private Integer id;
  private String name;
  private Integer league;
  private Integer season;
  private String country;
  private String code;
  private Integer venue;
  private String fullText;
}
