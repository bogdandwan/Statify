package org.example.statify.search.spec;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.statify.entity.*;
import org.example.statify.search.FixtureSearch;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class FixtureSpec implements Specification<DBFixture> {

  private final FixtureSearch search;

  @Override
  public Predicate toPredicate(Root<DBFixture> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

    final List<Predicate> predicates = new ArrayList<>();

    if (search.getId() != null) {
      predicates.add(cb.equal(root.get(DBFixture_.id), search.getId()));
    }

    if (search.getApiId() != null) {
      predicates.add(cb.equal(root.get(DBFixture_.apiId), search.getApiId()));
    }

    if (search.getReferee() != null) {
      predicates.add(
          cb.like(
              cb.lower(root.get(DBFixture_.referee)),
              "%" + search.getReferee().toLowerCase() + "%"));
    }

    if (search.getTimezone() != null) {
      predicates.add(cb.equal(root.get(DBFixture_.timezone), search.getTimezone()));
    }

    if (search.getDateFrom() != null) {
      predicates.add(cb.greaterThanOrEqualTo(root.get(DBFixture_.date), search.getDateFrom()));
    }

    if (search.getDateTo() != null) {
      predicates.add(cb.lessThanOrEqualTo(root.get(DBFixture_.date), search.getDateTo()));
    }

    if (search.getStatusLong() != null) {
      predicates.add(cb.equal(root.get(DBFixture_.statusLong), search.getStatusLong()));
    }

    if (search.getStatusShort() != null) {
      predicates.add(cb.equal(root.get(DBFixture_.statusShort), search.getStatusShort()));
    }

    if (search.getLeagueId() != null) {
      predicates.add(cb.equal(root.get(DBFixture_.league).get(DBLeague_.id), search.getLeagueId()));
    }

    if (search.getLeagueApiId() != null) {
      predicates.add(
          cb.equal(root.get(DBFixture_.league).get(DBLeague_.apiId), search.getLeagueApiId()));
    }

    if (search.getSeasonId() != null) {
      predicates.add(cb.equal(root.get(DBFixture_.season).get(DBSeason_.id), search.getSeasonId()));
    }

    if (search.getSeasonYear() != null) {
      predicates.add(
          cb.equal(root.get(DBFixture_.season).get(DBSeason_.year), search.getSeasonYear()));
    }

    if (search.getVenueId() != null) {
      predicates.add(cb.equal(root.get(DBFixture_.venue).get(DBVenue_.id), search.getVenueId()));
    }

    if (search.getVenueApiId() != null) {
      predicates.add(
          cb.equal(root.get(DBFixture_.venue).get(DBVenue_.apiId), search.getVenueApiId()));
    }

    if (search.getHomeTeamId() != null) {
      predicates.add(
          cb.equal(root.get(DBFixture_.homeTeam).get(DBTeam_.id), search.getHomeTeamId()));
    }

    if (search.getAwayTeamId() != null) {
      predicates.add(
          cb.equal(root.get(DBFixture_.awayTeam).get(DBTeam_.id), search.getAwayTeamId()));
    }

    if (search.getTeamId() != null) {

      Predicate homeTeam =
          cb.equal(root.get(DBFixture_.homeTeam).get(DBTeam_.id), search.getTeamId());

      Predicate awayTeam =
          cb.equal(root.get(DBFixture_.awayTeam).get(DBTeam_.id), search.getTeamId());

      predicates.add(cb.or(homeTeam, awayTeam));
    }

    if (search.getHomeTeamApiId() != null) {
      predicates.add(
          cb.equal(root.get(DBFixture_.homeTeam).get(DBTeam_.apiId), search.getHomeTeamApiId()));
    }

    if (search.getAwayTeamApiId() != null) {
      predicates.add(
          cb.equal(root.get(DBFixture_.awayTeam).get(DBTeam_.apiId), search.getAwayTeamApiId()));
    }

    if (search.getTeamApiId() != null) {

      Predicate homeTeam =
          cb.equal(root.get(DBFixture_.homeTeam).get(DBTeam_.apiId), search.getTeamApiId());

      Predicate awayTeam =
          cb.equal(root.get(DBFixture_.awayTeam).get(DBTeam_.apiId), search.getTeamApiId());

      predicates.add(cb.or(homeTeam, awayTeam));
    }

    return cb.and(predicates.toArray(new Predicate[0]));
  }
}
