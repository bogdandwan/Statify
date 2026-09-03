package org.example.statify.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class PlayerSearch {

  private Integer id;
  private Integer team;
  private Integer league;
  private Integer season;
  private String fullText;
  private Integer page;
}
