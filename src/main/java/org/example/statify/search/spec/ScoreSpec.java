package org.example.statify.search.spec;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.statify.entity.DBFixture_;
import org.example.statify.entity.DBScore;
import org.example.statify.entity.DBScore_;
import org.example.statify.search.ScoreSearch;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class ScoreSpec implements Specification<DBScore> {

  private final ScoreSearch search;

  @Override
  public Predicate toPredicate(Root<DBScore> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

    final List<Predicate> predicates = new ArrayList<>();

    if (search.getId() != null) {
      predicates.add(cb.equal(root.get(DBScore_.id), search.getId()));
    }

    if (search.getType() != null) {
      predicates.add(cb.equal(root.get(DBScore_.type), search.getType()));
    }

    if (search.getHome() != null) {
      predicates.add(cb.equal(root.get(DBScore_.home), search.getHome()));
    }

    if (search.getHomeFrom() != null) {
      predicates.add(cb.greaterThanOrEqualTo(root.get(DBScore_.home), search.getHomeFrom()));
    }

    if (search.getHomeTo() != null) {
      predicates.add(cb.lessThanOrEqualTo(root.get(DBScore_.home), search.getHomeTo()));
    }

    if (search.getAway() != null) {
      predicates.add(cb.equal(root.get(DBScore_.away), search.getAway()));
    }

    if (search.getAwayFrom() != null) {
      predicates.add(cb.greaterThanOrEqualTo(root.get(DBScore_.away), search.getAwayFrom()));
    }

    if (search.getAwayTo() != null) {
      predicates.add(cb.lessThanOrEqualTo(root.get(DBScore_.away), search.getAwayTo()));
    }

    if (search.getFixtureId() != null) {
      predicates.add(
          cb.equal(root.get(DBScore_.fixture).get(DBFixture_.id), search.getFixtureId()));
    }

    if (search.getFixtureApiId() != null) {
      predicates.add(
          cb.equal(root.get(DBScore_.fixture).get(DBFixture_.apiId), search.getFixtureApiId()));
    }

    return cb.and(predicates.toArray(new Predicate[0]));
  }
}
