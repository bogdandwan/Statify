package org.example.statify.repository;

import org.example.statify.entity.DBPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PlayerRepository
    extends JpaRepository<DBPlayer, Long>, JpaSpecificationExecutor<DBPlayer> {

  DBPlayer findByApiId(Integer apiId);

  boolean existsByApiId(Integer apiId);
}
