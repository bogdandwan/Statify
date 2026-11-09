package org.example.statify.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.example.statify.model.LeagueModel;

@Entity
@Getter
@Setter
@Table(name = "leagues")
@Accessors(chain = true)
public class DBLeague {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "api_id", unique = true)
  private Integer apiId;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "type", nullable = false)
  private String type;

  @Column(name = "logo", nullable = false)
  private String logo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "country_id")
  private DBCountry country;

  @OneToMany(mappedBy = "league", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<DBSeason> seasons = new ArrayList<>();

  public static DBLeague withId(LeagueModel leagueModel) {
    return new DBLeague().setId(leagueModel.getId());
  }
}
