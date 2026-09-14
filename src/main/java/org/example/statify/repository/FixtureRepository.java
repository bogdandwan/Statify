package org.example.statify.repository;

import org.example.statify.entity.DBFixture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FixtureRepository
    extends JpaRepository<DBFixture, Long>, JpaSpecificationExecutor<DBFixture> {

  DBFixture findByApiId(Integer apiId);

  boolean existsByApiId(Integer apiId);
}
