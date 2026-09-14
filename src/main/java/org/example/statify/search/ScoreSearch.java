package org.example.statify.search;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.example.statify.entity.enums.ScoreType;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ScoreSearch {

  private Long id;

  private ScoreType type;

  private Integer home;
  private Integer homeFrom;
  private Integer homeTo;

  private Integer away;
  private Integer awayFrom;
  private Integer awayTo;

  private Long fixtureId;
  private Long fixtureApiId;
}
