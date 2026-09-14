package org.example.statify.search;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class LeagueSearch {

  private Long id;
  private Integer apiId;
  private String name;
  private String type;
  private Long countryId;
  private String countryName;
}
