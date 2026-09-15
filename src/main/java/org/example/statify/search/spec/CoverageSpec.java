package org.example.statify.search.spec;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.statify.entity.DBCoverage;
import org.example.statify.entity.DBCoverage_;
import org.example.statify.entity.DBLeague_;
import org.example.statify.entity.DBSeason_;
import org.example.statify.search.CoverageSearch;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class CoverageSpec implements Specification<DBCoverage> {

  private final CoverageSearch search;

  @Override
  public Predicate toPredicate(Root<DBCoverage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

    final List<Predicate> predicates = new ArrayList<>();

    if (search.getId() != null) {
      predicates.add(cb.equal(root.get(DBCoverage_.id), search.getId()));
    }

    if (search.getStandings() != null) {
      predicates.add(cb.equal(root.get(DBCoverage_.standings), search.getStandings()));
    }

    if (search.getPlayers() != null) {
      predicates.add(cb.equal(root.get(DBCoverage_.players), search.getPlayers()));
    }

    if (search.getTopScorers() != null) {
      predicates.add(cb.equal(root.get(DBCoverage_.topScorers), search.getTopScorers()));
    }

    if (search.getTopAssists() != null) {
      predicates.add(cb.equal(root.get(DBCoverage_.topAssists), search.getTopAssists()));
    }

    if (search.getTopCards() != null) {
      predicates.add(cb.equal(root.get(DBCoverage_.topCards), search.getTopCards()));
    }

    if (search.getInjuries() != null) {
      predicates.add(cb.equal(root.get(DBCoverage_.injuries), search.getInjuries()));
    }

    if (search.getPredictions() != null) {
      predicates.add(cb.equal(root.get(DBCoverage_.predictions), search.getPredictions()));
    }

    if (search.getOdds() != null) {
      predicates.add(cb.equal(root.get(DBCoverage_.odds), search.getOdds()));
    }

    if (search.getSeasonId() != null) {
      predicates.add(
          cb.equal(root.get(DBCoverage_.season).get(DBSeason_.id), search.getSeasonId()));
    }

    if (search.getSeasonYear() != null) {
      predicates.add(
          cb.equal(root.get(DBCoverage_.season).get(DBSeason_.year), search.getSeasonYear()));
    }

    if (search.getLeagueId() != null) {
      predicates.add(
          cb.equal(
              root.get(DBCoverage_.season).get(DBSeason_.league).get(DBLeague_.id),
              search.getLeagueId()));
    }

    return cb.and(predicates.toArray(new Predicate[0]));
  }
}
