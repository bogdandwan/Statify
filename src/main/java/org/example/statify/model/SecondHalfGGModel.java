package org.example.statify.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class SecondHalfGGModel {

    private Long teamId;
    private Long teamApiId;
    private String teamName;
    private Long ggCount;
    private Long matchesCount;
    private BigDecimal percentage;

}
