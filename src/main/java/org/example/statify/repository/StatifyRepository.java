package org.example.statify.repository;

import java.util.List;
import org.example.statify.entity.DBFixture;
import org.example.statify.projection.SecondHalfGGProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StatifyRepository extends JpaRepository<DBFixture, Long> {

  @Query(
      value =
          """
                    WITH team_matches AS (

                        SELECT
                            f.id AS fixture_id,
                            f.home_team_id AS team_id,
                            f.date AS match_date,
                            CASE
                                WHEN s.home > 0 AND s.away > 0 THEN 1
                                ELSE 0
                            END AS second_half_gg
                        FROM fixtures f
                        LEFT JOIN scores s
                            ON s.fixture_id = f.id
                            AND s.type = 'SECOND_HALF'
                        WHERE f.status_short = 'FT'

                        UNION ALL

                        SELECT
                            f.id AS fixture_id,
                            f.away_team_id AS team_id,
                            f.date AS match_date,
                            CASE
                                WHEN s.home > 0 AND s.away > 0 THEN 1
                                ELSE 0
                            END AS second_half_gg
                        FROM fixtures f
                        LEFT JOIN scores s
                            ON s.fixture_id = f.id
                            AND s.type = 'SECOND_HALF'
                        WHERE f.status_short = 'FT'
                    ),

                    ranked_matches AS (

                        SELECT
                            team_id,
                            fixture_id,
                            match_date,
                            second_half_gg,
                            ROW_NUMBER() OVER (
                                PARTITION BY team_id
                                ORDER BY match_date DESC
                            ) AS row_num
                        FROM team_matches
                    )

                    SELECT
                        t.id AS teamId,
                        t.api_id AS teamApiId,
                        t.name AS teamName,
                        SUM(r.second_half_gg) AS ggCount,
                        COUNT(*) AS matchesCount,
                        ROUND(
                            SUM(r.second_half_gg) * 100.0 / COUNT(*),
                            2
                        ) AS percentage
                    FROM ranked_matches r
                    JOIN teams t
                        ON t.id = r.team_id
                    WHERE r.row_num <= :lastMatches
                    GROUP BY
                        t.id,
                        t.api_id,
                        t.name
                    ORDER BY
                        ggCount DESC,
                        percentage DESC
                    LIMIT :limit
                    """,
      nativeQuery = true)
  List<SecondHalfGGProjection> findTopSecondHalfGG(
      @Param("lastMatches") Integer lastMatches, @Param("limit") Integer limit);
}
