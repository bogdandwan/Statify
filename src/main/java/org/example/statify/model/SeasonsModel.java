package org.example.statify.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SeasonsModel {

    private Integer year;
    private LocalDate start;
    private LocalDate end;
    private Boolean current;

}
