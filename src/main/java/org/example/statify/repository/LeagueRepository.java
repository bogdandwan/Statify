package org.example.statify.repository;

import org.example.statify.entity.DBLeague;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LeagueRepository
    extends JpaRepository<DBLeague, Long>, JpaSpecificationExecutor<DBLeague> {

  DBLeague findByApiId(Integer apiId);

  boolean existsByApiId(Integer apiId);
}
