package org.example.statify.model;

import lombok.Getter;
import lombok.Setter;
import org.example.statify.entity.enums.ScoreType;

@Getter
@Setter
public class ScoreModel {

    private Long id;
    private Integer home;
    private Integer away;
    private ScoreType type;
}
