package org.example.statify.search.spec;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.example.statify.entity.DBFixture;
import org.example.statify.search.FixtureSearch;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FixtureSpec implements Specification<DBFixture> {

    private final FixtureSearch search;

    @Override
    public Predicate toPredicate(Root<DBFixture> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        if (search.getStatusShort() != null) {
            predicates.add(criteriaBuilder.equal(root.get("statusShort"), search.getStatusShort()));
        }

        if (search.getLeagueId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("league").get("id"), search.getLeagueId()));
        }

        if (search.getSeasonYear() != null) {
            predicates.add(criteriaBuilder.equal(root.get("season").get("year"), search.getSeasonYear()));
        }

        if (search.getDateFrom() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), search.getDateFrom()));
        }

        if (search.getDateTo() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), search.getDateTo()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
