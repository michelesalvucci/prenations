package com.michelesalvucci.prenations.service.filter.field;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class PNLongFilterField extends PNFilterField<Long> {

    @Override
    public Class<Long> getType() {
        return Long.class;
    }

    @Override
    public Specification<Long> createSpecification(String attributeName) {
        return new Specification<Long>() {
            @Override
            public Predicate toPredicate(Root<Long> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate p = cb.conjunction();
                p = cb.and(p, cb.equal(root.get(attributeName), getValue()));
                return p;
            }
        };
    }
}
