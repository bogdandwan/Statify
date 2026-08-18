package org.example.statify.repository;

import org.example.statify.entity.DBSeason;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeasonRepository extends JpaRepository<DBSeason, Long> {

    Optional<DBSeason> findByLeagueApiIdYear(Long leagueApiId, Integer year);

}
