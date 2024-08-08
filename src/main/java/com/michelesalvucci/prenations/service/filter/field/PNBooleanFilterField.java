package com.michelesalvucci.prenations.service.filter.field;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class PNBooleanFilterField extends PNFilterField<Boolean> {

    @Override
    public Class<Boolean> getType() {
        return Boolean.class;
    }

    @Override
    public Specification<Boolean> createSpecification(String attributeName) {
        return new Specification<Boolean>() {
            @Override
            public Predicate toPredicate(Root<Boolean> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate p = cb.conjunction();
                p = cb.and(p, cb.equal(root.get(attributeName), getValue()));
                return p;
            }
        };
    }
}
