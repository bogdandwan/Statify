package org.example.statify.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "seasons")
public class DBSeasons {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "start", nullable = false)
    private LocalDate start;

    @Column(name = "end", nullable = false)
    private LocalDate end;

    @Column(name = "current", nullable = false)
    private Boolean current;

}
