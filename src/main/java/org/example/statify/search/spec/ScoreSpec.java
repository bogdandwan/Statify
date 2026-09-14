package org.example.statify.search.spec;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.statify.entity.DBScore;
import org.example.statify.search.ScoreSearch;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class ScoreSpec implements Specification<DBScore> {

  private final ScoreSearch search;

  @Override
  public Predicate toPredicate(Root<DBScore> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

    final List<Predicate> predicates = new ArrayList<>();

    if (search.getId() != null) {
      predicates.add(cb.equal(root.get("id"), search.getId()));
    }

    if (search.getType() != null) {
      predicates.add(cb.equal(root.get("type"), search.getType()));
    }

    if (search.getHome() != null) {
      predicates.add(cb.equal(root.get("home"), search.getHome()));
    }

    if (search.getHomeFrom() != null) {
      predicates.add(cb.greaterThanOrEqualTo(root.get("home"), search.getHomeFrom()));
    }

    if (search.getHomeTo() != null) {
      predicates.add(cb.lessThanOrEqualTo(root.get("home"), search.getHomeTo()));
    }

    if (search.getAway() != null) {
      predicates.add(cb.equal(root.get("away"), search.getAway()));
    }

    if (search.getAwayFrom() != null) {
      predicates.add(cb.greaterThanOrEqualTo(root.get("away"), search.getAwayFrom()));
    }

    if (search.getAwayTo() != null) {
      predicates.add(cb.lessThanOrEqualTo(root.get("away"), search.getAwayTo()));
    }

    if (search.getFixtureId() != null) {
      predicates.add(cb.equal(root.get("fixture").get("id"), search.getFixtureId()));
    }

    if (search.getFixtureApiId() != null) {
      predicates.add(cb.equal(root.get("fixture").get("apiId"), search.getFixtureApiId()));
    }

    return cb.and(predicates.toArray(new Predicate[0]));
  }
}
