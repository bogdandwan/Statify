package org.example.statify.repository;

import org.example.statify.entity.DBFixture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FixtureRepository extends JpaRepository<DBFixture, Long> {

    Optional<DBFixture> findByApiId(Long apiId);

}
