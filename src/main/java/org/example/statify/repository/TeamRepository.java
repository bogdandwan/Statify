package org.example.statify.repository;

import org.example.statify.entity.DBTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TeamRepository
    extends JpaRepository<DBTeam, Long>, JpaSpecificationExecutor<DBTeam> {

  DBTeam findByApiId(Integer apiId);

  boolean existsByApiId(Integer apiId);
}
