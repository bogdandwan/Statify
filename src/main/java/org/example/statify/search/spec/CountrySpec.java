package org.example.statify.search.spec;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.example.statify.entity.DBCountry;
import org.example.statify.search.CountrySearch;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CountrySpec implements Specification<DBCountry> {

    private final CountrySearch search;

    @Override
    public Predicate toPredicate(Root<DBCountry> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        final List<Predicate> predicates = new ArrayList<>();

        if (search.getId() != null) {
            predicates.add(cb.equal(root.get("id"), search.getId()));
        }

        if (search.getName() != null) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + search.getName().toLowerCase() + "%"));
        }

        if (search.getCode() != null) {
            predicates.add(cb.equal(root.get("code"), search.getCode()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
