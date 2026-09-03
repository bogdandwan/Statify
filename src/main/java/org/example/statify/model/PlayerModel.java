package org.example.statify.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.statify.entity.DBPlayer;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PlayerModel {

    private Long id;
    private Integer apiId;
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

    public PlayerModel(DBPlayer dbPlayer) {
        this.id = dbPlayer.getId();
        this.apiId = dbPlayer.getApiId();
        this.name = dbPlayer.getName();
        this.firstname = dbPlayer.getFirstname();
        this.lastname = dbPlayer.getLastname();
        this.birthDate = dbPlayer.getBirthDate();
        this.birthPlace = dbPlayer.getBirthPlace();
        this.birthCountry = dbPlayer.getBirthCountry();
        this.nationality = dbPlayer.getNationality();
        this.height = dbPlayer.getHeight();
        this.weight = dbPlayer.getWeight();
        this.number = dbPlayer.getNumber();
        this.position = dbPlayer.getPosition();
        this.photo = dbPlayer.getPhoto();
    }
}
