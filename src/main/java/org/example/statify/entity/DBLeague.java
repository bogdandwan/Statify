package org.example.statify.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "leagues")
public class DBLeague {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "api_id", unique = true)
    private Long apiId;

    @Column(name = "name",  nullable = false)
    private String name;

    @Column(name = "type",  nullable = false)
    private String type;

    @Column(name = "logo",   nullable = false)
    private String logo;

    @ManyToOne
    private DBCountry country;

    @OneToMany(mappedBy = "league")
    private List<DBSeason> seasons;
}
