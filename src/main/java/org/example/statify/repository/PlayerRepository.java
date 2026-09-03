package org.example.statify.repository;

import org.example.statify.entity.DBPlayer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<DBPlayer, Long> {

  DBPlayer findByApiId(Integer apiId);

  boolean existsByApiId(Integer apiId);
}
