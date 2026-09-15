package org.example.statify.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.statify.entity.enums.ScoreType;

@Entity
@Getter
@Setter
@Table(name = "scores")
public class DBScore {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "home")
  private Integer home;

  @Column(name = "away")
  private Integer away;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false)
  private ScoreType type;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fixture_id", nullable = false)
  private DBFixture fixture;
}
