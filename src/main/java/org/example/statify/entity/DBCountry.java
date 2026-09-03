package org.example.statify.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "countries")
public class DBCountry {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "code", unique = true)
  private String code;

  @Column(name = "flag")
  private String flag;

  @OneToMany(mappedBy = "country")
  private List<DBLeague> leagues = new ArrayList<>();
}
