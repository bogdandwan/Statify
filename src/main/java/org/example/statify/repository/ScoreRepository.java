package org.example.statify.repository;

import org.example.statify.entity.DBScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ScoreRepository
    extends JpaRepository<DBScore, Long>, JpaSpecificationExecutor<DBScore> {}
