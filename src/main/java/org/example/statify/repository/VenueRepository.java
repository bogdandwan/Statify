package org.example.statify.repository;

import org.example.statify.entity.DBVenue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VenueRepository extends JpaRepository<DBVenue, Long> {

    Optional<DBVenue> findByApiId(Long apiId);

    boolean existsByApiId(Long apiId);

}
