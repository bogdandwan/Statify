package org.example.statify.search.spec;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.statify.entity.DBSeason;
import org.example.statify.search.SeasonSearch;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class SeasonSpec implements Specification<DBSeason> {

  private final SeasonSearch search;

  @Override
  public Predicate toPredicate(Root<DBSeason> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

    final List<Predicate> predicates = new ArrayList<>();

    if (search.getId() != null) {
      predicates.add(cb.equal(root.get("id"), search.getId()));
    }

    if (search.getYear() != null) {
      predicates.add(cb.equal(root.get("year"), search.getYear()));
    }

    if (search.getYearFrom() != null) {
      predicates.add(cb.greaterThanOrEqualTo(root.get("year"), search.getYearFrom()));
    }

    if (search.getYearTo() != null) {
      predicates.add(cb.lessThanOrEqualTo(root.get("year"), search.getYearTo()));
    }

    if (search.getStartFrom() != null) {
      predicates.add(cb.greaterThanOrEqualTo(root.get("start"), search.getStartFrom()));
    }

    if (search.getStartTo() != null) {
      predicates.add(cb.lessThanOrEqualTo(root.get("start"), search.getStartTo()));
    }

    if (search.getEndFrom() != null) {
      predicates.add(cb.greaterThanOrEqualTo(root.get("end"), search.getEndFrom()));
    }

    if (search.getEndTo() != null) {
      predicates.add(cb.lessThanOrEqualTo(root.get("end"), search.getEndTo()));
    }

    if (search.getCurrent() != null) {
      predicates.add(cb.equal(root.get("current"), search.getCurrent()));
    }

    if (search.getLeagueId() != null) {
      predicates.add(cb.equal(root.get("league").get("id"), search.getLeagueId()));
    }

    if (search.getLeagueApiId() != null) {
      predicates.add(cb.equal(root.get("league").get("apiId"), search.getLeagueApiId()));
    }

    return cb.and(predicates.toArray(new Predicate[0]));
  }
}
