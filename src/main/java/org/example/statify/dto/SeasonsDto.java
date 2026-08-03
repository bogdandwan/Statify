package org.example.statify.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SeasonsDto {

    private Integer year;

    private LocalDate start;

    private LocalDate end;

    private Boolean current;
}
