package org.example.statify.search;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TeamSearch {

  private Long id;
  private Long apiId;
  private String name;
  private String code;
  private String country;
  private Integer founded;
  private Integer foundedFrom;
  private Integer foundedTo;
  private Boolean national;
}
