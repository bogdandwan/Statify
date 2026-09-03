package org.example.statify.repository;

import java.util.Optional;
import org.example.statify.entity.DBFixture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FixtureRepository extends JpaRepository<DBFixture, Long> {

  Optional<DBFixture> findByApiId(Long apiId);

  boolean existsByApiId(Integer apiId);
}
