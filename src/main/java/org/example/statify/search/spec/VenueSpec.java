package org.example.statify.search.spec;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.statify.entity.DBVenue;
import org.example.statify.entity.DBVenue_;
import org.example.statify.search.VenueSearch;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class VenueSpec implements Specification<DBVenue> {

  private final VenueSearch search;

  @Override
  public Predicate toPredicate(Root<DBVenue> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

    final List<Predicate> predicates = new ArrayList<>();

    if (search.getId() != null) {
      predicates.add(cb.equal(root.get(DBVenue_.id), search.getId()));
    }

    if (search.getApiId() != null) {
      predicates.add(cb.equal(root.get(DBVenue_.apiId), search.getApiId()));
    }

    if (search.getName() != null) {
      predicates.add(
          cb.like(cb.lower(root.get(DBVenue_.name)), "%" + search.getName().toLowerCase() + "%"));
    }

    if (search.getAddress() != null) {
      predicates.add(
          cb.like(
              cb.lower(root.get(DBVenue_.address)), "%" + search.getAddress().toLowerCase() + "%"));
    }

    if (search.getCity() != null) {
      predicates.add(cb.equal(cb.lower(root.get(DBVenue_.city)), search.getCity().toLowerCase()));
    }

    if (search.getCountry() != null) {
      predicates.add(
          cb.equal(cb.lower(root.get(DBVenue_.country)), search.getCountry().toLowerCase()));
    }

    if (search.getSurface() != null) {
      predicates.add(
          cb.equal(cb.lower(root.get(DBVenue_.surface)), search.getSurface().toLowerCase()));
    }

    if (search.getCapacity() != null) {
      predicates.add(cb.equal(root.get(DBVenue_.capacity), search.getCapacity()));
    }

    if (search.getCapacityFrom() != null) {
      predicates.add(
          cb.greaterThanOrEqualTo(root.get(DBVenue_.capacity), search.getCapacityFrom()));
    }

    if (search.getCapacityTo() != null) {
      predicates.add(cb.lessThanOrEqualTo(root.get(DBVenue_.capacity), search.getCapacityTo()));
    }

    return cb.and(predicates.toArray(new Predicate[0]));
  }
}
