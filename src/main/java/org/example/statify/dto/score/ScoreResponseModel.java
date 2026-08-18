package org.example.statify.dto.score;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScoreResponseModel {

    private ScoreDetailResponseModel halftime;
    private ScoreDetailResponseModel fulltime;
    private ScoreDetailResponseModel extratime;
    private ScoreDetailResponseModel penalty;

}
