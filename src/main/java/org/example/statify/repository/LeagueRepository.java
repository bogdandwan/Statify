package org.example.statify.repository;

import org.example.statify.entity.DBLeague;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeagueRepository extends JpaRepository<DBLeague, Long> {

    DBLeague findByApiId(Integer apiId);

    boolean existsByApiId(Integer apiId);

}
