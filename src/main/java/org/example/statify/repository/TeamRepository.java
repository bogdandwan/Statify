package org.example.statify.repository;

import org.example.statify.entity.DBTeam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<DBTeam, Long> {

  DBTeam findByApiId(Integer apiId);

  boolean existsByApiId(Integer apiId);
}
