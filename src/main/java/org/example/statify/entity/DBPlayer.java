package org.example.statify.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "players")
public class DBPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "api_id", unique = true, nullable = false)
    private Integer apiId;

    @Column(name = "name")
    private String name;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "birth_place")
    private String birthPlace;

    @Column(name = "birth_country")
    private String birthCountry;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "height")
    private String height;

    @Column(name = "weight")
    private String weight;

    @Column(name = "number")
    private Integer number;

    @Column(name = "position")
    private String position;

    @Column(name = "photo")
    private String photo;

    /*@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "currentTeam_id")
    private DBTeam currentTeam;*/
}
