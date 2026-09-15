package org.example.statify.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.statify.entity.DBScore;
import org.example.statify.entity.enums.ScoreType;

@Getter
@Setter
@NoArgsConstructor
public class ScoreModel {

  private Long id;
  private Integer home;
  private Integer away;
  private ScoreType type;

  public ScoreModel(DBScore dbScore) {
    this.id = dbScore.getId();
    this.home = dbScore.getHome();
    this.away = dbScore.getAway();
    this.type = dbScore.getType();
  }
}
