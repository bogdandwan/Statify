package org.example.statify.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PlayerModel {

    private Long id;
    private String name;
    private String firstname;
    private String lastname;
    private LocalDate birthDate;
    private String birthPlace;
    private String birthCountry;
    private String nationality;
    private String height;
    private String weight;
    private Integer number;
    private String position;
    private String photo;
}
