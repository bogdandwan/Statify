package org.example.statify.repository;

import org.example.statify.entity.DBSeason;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeasonRepository extends JpaRepository<DBSeason, Long> {

  DBSeason findByLeagueApiIdAndYear(Integer leagueApiId, Integer year);
}
