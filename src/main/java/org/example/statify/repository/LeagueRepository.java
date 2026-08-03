package org.example.statify.repository;

import org.example.statify.entity.DBLeague;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueRepository extends JpaRepository<DBLeague, Integer> {
}
