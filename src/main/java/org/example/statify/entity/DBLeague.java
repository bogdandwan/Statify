package org.example.statify.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "leagues")
public class DBLeague {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",  nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type",  nullable = false)
    private Type type;

    @Column(name = "logo",   nullable = false)
    private String logo;
}
