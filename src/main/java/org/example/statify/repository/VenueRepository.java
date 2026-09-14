package org.example.statify.repository;

import org.example.statify.entity.DBVenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VenueRepository
    extends JpaRepository<DBVenue, Long>, JpaSpecificationExecutor<DBVenue> {

  DBVenue findByApiId(Integer apiId);

  boolean existsByApiId(Integer apiId);
}
