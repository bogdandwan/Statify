package org.example.statify.search.spec;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.statify.entity.DBLeague_;
import org.example.statify.entity.DBSeason;
import org.example.statify.entity.DBSeason_;
import org.example.statify.search.SeasonSearch;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class SeasonSpec implements Specification<DBSeason> {

  private final SeasonSearch search;

  @Override
  public Predicate toPredicate(Root<DBSeason> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

    final List<Predicate> predicates = new ArrayList<>();

    if (search.getId() != null) {
      predicates.add(cb.equal(root.get(DBSeason_.id), search.getId()));
    }

    if (search.getYear() != null) {
      predicates.add(cb.equal(root.get(DBSeason_.year), search.getYear()));
    }

    if (search.getYearFrom() != null) {
      predicates.add(cb.greaterThanOrEqualTo(root.get(DBSeason_.year), search.getYearFrom()));
    }

    if (search.getYearTo() != null) {
      predicates.add(cb.lessThanOrEqualTo(root.get(DBSeason_.year), search.getYearTo()));
    }

    if (search.getStartFrom() != null) {
      predicates.add(cb.greaterThanOrEqualTo(root.get(DBSeason_.start), search.getStartFrom()));
    }

    if (search.getStartTo() != null) {
      predicates.add(cb.lessThanOrEqualTo(root.get(DBSeason_.start), search.getStartTo()));
    }

    if (search.getEndFrom() != null) {
      predicates.add(cb.greaterThanOrEqualTo(root.get(DBSeason_.end), search.getEndFrom()));
    }

    if (search.getEndTo() != null) {
      predicates.add(cb.lessThanOrEqualTo(root.get(DBSeason_.end), search.getEndTo()));
    }

    if (search.getCurrent() != null) {
      predicates.add(cb.equal(root.get(DBSeason_.current), search.getCurrent()));
    }

    if (search.getLeagueId() != null) {
      predicates.add(cb.equal(root.get(DBSeason_.league).get(DBLeague_.id), search.getLeagueId()));
    }

    if (search.getLeagueApiId() != null) {
      predicates.add(
          cb.equal(root.get(DBSeason_.league).get(DBLeague_.apiId), search.getLeagueApiId()));
    }

    return cb.and(predicates.toArray(new Predicate[0]));
  }
}
