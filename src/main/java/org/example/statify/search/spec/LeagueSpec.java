package org.example.statify.search.spec;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.statify.entity.DBCountry_;
import org.example.statify.entity.DBLeague;
import org.example.statify.entity.DBLeague_;
import org.example.statify.search.LeagueSearch;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class LeagueSpec implements Specification<DBLeague> {

  private final LeagueSearch search;

  @Override
  public Predicate toPredicate(Root<DBLeague> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

    final List<Predicate> predicates = new ArrayList<>();

    if (search.getId() != null) {
      predicates.add(cb.equal(root.get(DBLeague_.id), search.getId()));
    }

    if (search.getApiId() != null) {
      predicates.add(cb.equal(root.get(DBLeague_.apiId), search.getApiId()));
    }

    if (search.getName() != null) {
      predicates.add(
          cb.like(cb.lower(root.get(DBLeague_.name)), "%" + search.getName().toLowerCase() + "%"));
    }

    if (search.getType() != null) {
      predicates.add(cb.equal(root.get(DBLeague_.type), search.getType()));
    }

    if (search.getCountryId() != null) {
      predicates.add(
          cb.equal(root.get(DBLeague_.country).get(DBCountry_.id), search.getCountryId()));
    }

    if (search.getCountryName() != null) {
      predicates.add(
          cb.like(
              cb.lower(root.get(DBLeague_.country).get(DBCountry_.name)),
              "%" + search.getCountryName().toLowerCase() + "%"));
    }

    return cb.and(predicates.toArray(new Predicate[0]));
  }
}
