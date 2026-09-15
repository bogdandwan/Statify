package org.example.statify.repository;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import org.example.statify.entity.DBFixture;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FixtureRepository
    extends JpaRepository<DBFixture, Long>, JpaSpecificationExecutor<DBFixture> {

  DBFixture findByApiId(Integer apiId);

  boolean existsByApiId(Integer apiId);

  @EntityGraph(attributePaths = "scores")
  List<DBFixture> findByDateGreaterThanEqualAndDateLessThanAndStatusShortIn(
      OffsetDateTime from, OffsetDateTime to, Collection<String> statuses);
}
