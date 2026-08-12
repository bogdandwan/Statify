package org.example.statify.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "coverages")
public class DBCoverage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "standings",  nullable = false)
    private Boolean standings;

    @Column(name = "players",  nullable = false)
    private Boolean players;

    @Column(name = "topScorers",  nullable = false)
    private Boolean topScorers;

    @Column(name = "topAssists",  nullable = false)
    private Boolean topAssists;

    @Column(name = "topCards",  nullable = false)
    private Boolean topCards;

    @Column(name = "injuries",  nullable = false)
    private Boolean injuries;

    @Column(name = "predictions",  nullable = false)
    private Boolean predictions;

    @Column(name = "odds",  nullable = false)
    private Boolean odds;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fixtures_id")
    private DBFixture fixture;
}
