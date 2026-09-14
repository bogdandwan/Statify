package org.example.statify.repository;

import java.util.List;
import org.example.statify.entity.DBSeason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SeasonRepository
    extends JpaRepository<DBSeason, Long>, JpaSpecificationExecutor<DBSeason> {

  DBSeason findByLeagueApiIdAndYear(Integer leagueApiId, Integer year);

  List<DBSeason> findAllByCurrentTrue();
}
