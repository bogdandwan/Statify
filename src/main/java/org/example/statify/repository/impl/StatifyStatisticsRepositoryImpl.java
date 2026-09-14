package org.example.statify.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.example.statify.entity.DBFixture;
import org.example.statify.entity.DBScore;
import org.example.statify.entity.DBTeam;
import org.example.statify.model.TeamGGStatisticsModel;
import org.example.statify.repository.StatifyStatisticsRepository;
import org.example.statify.search.GGStatisticsSearch;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StatifyStatisticsRepositoryImpl implements StatifyStatisticsRepository {

  private final EntityManager entityManager;

  @Override
  public List<TeamGGStatisticsModel> findGGStatistics(GGStatisticsSearch search) {

    CriteriaBuilder cb = entityManager.getCriteriaBuilder();

    CriteriaQuery<Tuple> query = cb.createTupleQuery();

    Root<DBFixture> fixture = query.from(DBFixture.class);

    Join<DBFixture, DBTeam> homeTeam = fixture.join("homeTeam", JoinType.INNER);

    Join<DBFixture, DBTeam> awayTeam = fixture.join("awayTeam", JoinType.INNER);

    Join<DBFixture, DBScore> score = fixture.join("scores", JoinType.LEFT);

    /*
     * LEFT JOIN scores
     * ON score.type = traženi tip
     */
    if (search.getScoreType() != null) {
      score.on(cb.equal(score.get("type"), search.getScoreType()));
    }

    /*
     * Šta želimo da dobijemo iz baze.
     */
    query.select(
        cb.tuple(
            fixture.get("id").alias("fixtureId"),
            fixture.get("date").alias("matchDate"),
            homeTeam.get("id").alias("homeTeamId"),
            homeTeam.get("apiId").alias("homeTeamApiId"),
            homeTeam.get("name").alias("homeTeamName"),
            awayTeam.get("id").alias("awayTeamId"),
            awayTeam.get("apiId").alias("awayTeamApiId"),
            awayTeam.get("name").alias("awayTeamName"),
            score.get("home").alias("scoreHome"),
            score.get("away").alias("scoreAway")));

    final List<Predicate> predicates = new ArrayList<>();

    /*
     * Za statistiku računamo samo završene utakmice.
     */
    predicates.add(cb.equal(fixture.get("statusShort"), "FT"));

    /*
     * Liga.
     */
    if (search.getLeagueApiId() != null) {

      predicates.add(cb.equal(fixture.get("league").get("apiId"), search.getLeagueApiId()));
    }

    /*
     * Sezona.
     */
    if (search.getSeasonYear() != null) {

      predicates.add(cb.equal(fixture.get("season").get("year"), search.getSeasonYear()));
    }

    /*
     * Datum od.
     */
    if (search.getDateFrom() != null) {

      predicates.add(cb.greaterThanOrEqualTo(fixture.get("date"), search.getDateFrom()));
    }

    /*
     * Datum do.
     */
    if (search.getDateTo() != null) {

      predicates.add(cb.lessThanOrEqualTo(fixture.get("date"), search.getDateTo()));
    }

    query.where(cb.and(predicates.toArray(new Predicate[0])));

    /*
     * Najnovije utakmice prve.
     */
    query.orderBy(cb.desc(fixture.get("date")));

    List<Tuple> rows = entityManager.createQuery(query).getResultList();

    return calculateStatistics(rows, search);
  }

  private List<TeamGGStatisticsModel> calculateStatistics(
      List<Tuple> rows, GGStatisticsSearch search) {

    List<TeamMatch> teamMatches = new ArrayList<>();

    for (Tuple row : rows) {

      Integer homeScore = row.get("scoreHome", Integer.class);

      Integer awayScore = row.get("scoreAway", Integer.class);

      boolean gg = homeScore != null && awayScore != null && homeScore > 0 && awayScore > 0;

      Long fixtureId = row.get("fixtureId", Long.class);

      OffsetDateTime matchDate = row.get("matchDate", OffsetDateTime.class);

      /*
       * Jedna utakmica pripada home timu.
       */
      teamMatches.add(
          new TeamMatch(
              fixtureId,
              row.get("homeTeamId", Long.class),
              row.get("homeTeamApiId", Integer.class),
              row.get("homeTeamName", String.class),
              matchDate,
              gg));

      /*
       * Ista utakmica pripada i away timu.
       */
      teamMatches.add(
          new TeamMatch(
              fixtureId,
              row.get("awayTeamId", Long.class),
              row.get("awayTeamApiId", Integer.class),
              row.get("awayTeamName", String.class),
              matchDate,
              gg));
    }

    Map<Long, List<TeamMatch>> matchesByTeam =
        teamMatches.stream().collect(Collectors.groupingBy(TeamMatch::teamId));

    List<TeamGGStatisticsModel> statistics = new ArrayList<>();

    for (List<TeamMatch> matches : matchesByTeam.values()) {

      List<TeamMatch> selectedMatches = selectMatches(matches, search.getLastMatches());

      if (selectedMatches.isEmpty()) {
        continue;
      }

      long ggCount = selectedMatches.stream().filter(TeamMatch::gg).count();

      long matchesCount = selectedMatches.size();

      BigDecimal percentage =
          BigDecimal.valueOf(ggCount)
              .multiply(BigDecimal.valueOf(100))
              .divide(BigDecimal.valueOf(matchesCount), 2, RoundingMode.HALF_UP);

      TeamMatch team = selectedMatches.get(0);

      statistics.add(
          new TeamGGStatisticsModel(
              team.teamId(), team.teamApiId(), team.teamName(), ggCount, matchesCount, percentage));
    }

    Stream<TeamGGStatisticsModel> stream =
        statistics.stream()
            .sorted(
                Comparator.comparing(TeamGGStatisticsModel::getPercentage)
                    .reversed()
                    .thenComparing(TeamGGStatisticsModel::getGgCount, Comparator.reverseOrder()));

    /*
     * Ako limit nije poslat,
     * vraćamo sve timove.
     */
    if (search.getLimit() != null) {
      stream = stream.limit(search.getLimit());
    }

    return stream.toList();
  }

  private List<TeamMatch> selectMatches(List<TeamMatch> matches, Integer lastMatches) {

    Stream<TeamMatch> stream =
        matches.stream().sorted(Comparator.comparing(TeamMatch::matchDate).reversed());

    /*
     * Ako lastMatches == null,
     * koristimo celu ligu/sezonu.
     */
    if (lastMatches != null) {
      stream = stream.limit(lastMatches);
    }

    return stream.toList();
  }

  private record TeamMatch(
      Long fixtureId,
      Long teamId,
      Integer teamApiId,
      String teamName,
      OffsetDateTime matchDate,
      boolean gg) {}
}
