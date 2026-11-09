package org.example.statify.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.statify.entity.enums.GameType;

@Entity
@Getter
@Setter
@Table(name = "game_types")
public class DBGame {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, unique = true)
  private GameType type;

  @Column(nullable = false)
  private String name;
}
