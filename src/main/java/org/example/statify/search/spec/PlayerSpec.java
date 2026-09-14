package org.example.statify.search.spec;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.statify.entity.DBPlayer;
import org.example.statify.search.PlayerSearch;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class PlayerSpec implements Specification<DBPlayer> {

  private final PlayerSearch search;

  @Override
  public Predicate toPredicate(Root<DBPlayer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

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

    if (search.getFirstname() != null) {
      predicates.add(
          cb.like(
              cb.lower(root.get("firstname")), "%" + search.getFirstname().toLowerCase() + "%"));
    }

    if (search.getLastname() != null) {
      predicates.add(
          cb.like(cb.lower(root.get("lastname")), "%" + search.getLastname().toLowerCase() + "%"));
    }

    if (search.getBirthDateFrom() != null) {
      predicates.add(cb.greaterThanOrEqualTo(root.get("birthDate"), search.getBirthDateFrom()));
    }

    if (search.getBirthDateTo() != null) {
      predicates.add(cb.lessThanOrEqualTo(root.get("birthDate"), search.getBirthDateTo()));
    }

    if (search.getBirthPlace() != null) {
      predicates.add(
          cb.like(
              cb.lower(root.get("birthPlace")), "%" + search.getBirthPlace().toLowerCase() + "%"));
    }

    if (search.getBirthCountry() != null) {
      predicates.add(
          cb.equal(cb.lower(root.get("birthCountry")), search.getBirthCountry().toLowerCase()));
    }

    if (search.getNationality() != null) {
      predicates.add(
          cb.equal(cb.lower(root.get("nationality")), search.getNationality().toLowerCase()));
    }

    if (search.getPosition() != null) {
      predicates.add(cb.equal(cb.lower(root.get("position")), search.getPosition().toLowerCase()));
    }

    if (search.getNumber() != null) {
      predicates.add(cb.equal(root.get("number"), search.getNumber()));
    }

    return cb.and(predicates.toArray(new Predicate[0]));
  }
}
