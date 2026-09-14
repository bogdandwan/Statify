package org.example.statify.search.spec;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.statify.entity.DBTeam;
import org.example.statify.search.TeamSearch;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class TeamSpec implements Specification<DBTeam> {

  private final TeamSearch search;

  @Override
  public Predicate toPredicate(Root<DBTeam> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

    final List<Predicate> predicates = new ArrayList<>();

    if (search.getId() != null) {
      predicates.add(cb.equal(root.get("id"), search.getId()));
    }

    if (search.getApiId() != null) {
      predicates.add(cb.equal(root.get("apiId"), search.getApiId()));
    }

    if (search.getName() != null) {
      predicates.add(
          cb.like(cb.lower(root.get("name")), "%" + search.getName().toLowerCase() + "%"));
    }

    if (search.getCode() != null) {
      predicates.add(cb.equal(root.get("code"), search.getCode()));
    }

    if (search.getCountry() != null) {
      predicates.add(cb.equal(cb.lower(root.get("country")), search.getCountry().toLowerCase()));
    }

    if (search.getFounded() != null) {
      predicates.add(cb.equal(root.get("founded"), search.getFounded()));
    }

    if (search.getFoundedFrom() != null) {
      predicates.add(cb.greaterThanOrEqualTo(root.get("founded"), search.getFoundedFrom()));
    }

    if (search.getFoundedTo() != null) {
      predicates.add(cb.lessThanOrEqualTo(root.get("founded"), search.getFoundedTo()));
    }

    if (search.getNational() != null) {
      predicates.add(cb.equal(root.get("national"), search.getNational()));
    }

    return cb.and(predicates.toArray(new Predicate[0]));
  }
}
