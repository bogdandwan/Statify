package org.example.statify.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class ApiLeagueSearch {

  private Integer id;
  private String name;
  private String country;
  private String code;
  private String season;
  private Integer team;
  private String type;
  private String current;
  private String fullText;
  private String last;
}
