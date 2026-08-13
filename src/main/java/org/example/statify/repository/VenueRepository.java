package org.example.statify.repository;

import org.example.statify.entity.DBVenue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<DBVenue, Long> {
}
