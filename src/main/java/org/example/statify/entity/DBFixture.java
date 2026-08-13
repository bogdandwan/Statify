package org.example.statify.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "fixtures")
public class DBFixture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "events",  nullable = false)
    private Boolean events;

    @Column(name = "lineups",  nullable = false)
    private Boolean lineups;

    @Column(name = "statisticsFixtures",  nullable = false)
    private Boolean statisticsFixtures;

    @Column(name = "statisticsPlayers",  nullable = false)
    private Boolean statisticsPlayers;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coverage_id", nullable = false, unique = true)
    private DBCoverage coverage;
}
