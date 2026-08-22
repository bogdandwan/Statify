package org.example.statify.repository;

import org.example.statify.entity.DBVenue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VenueRepository extends JpaRepository<DBVenue, Long> {

    DBVenue findByApiId(Integer apiId);

    boolean existsByApiId(Integer apiId);

}
