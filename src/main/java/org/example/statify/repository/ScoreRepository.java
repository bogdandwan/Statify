package org.example.statify.repository;

import org.example.statify.entity.DBScore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<DBScore, Long> {}
