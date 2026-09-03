package org.example.statify.repository;

import org.example.statify.entity.DBPlayer;
import org.example.statify.entity.DBVenue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<DBPlayer, Long> {

    DBPlayer findByApiId(Integer apiId);

    boolean existsByApiId(Integer apiId);
}
