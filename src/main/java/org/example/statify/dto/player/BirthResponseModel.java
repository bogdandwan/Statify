package org.example.statify.dto.player;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BirthResponseModel {

    private LocalDate date;
    private String place;
    private String country;
}
