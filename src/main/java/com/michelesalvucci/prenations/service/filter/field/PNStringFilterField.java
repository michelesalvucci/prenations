package com.michelesalvucci.prenations.service.filter.field;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class PNStringFilterField extends PNFilterField<String> {

    @Override
    public Class<String> getType() {
       return String.class;
    }

    @Override
    public Specification<String> createSpecification(String attributeName) {
        return new Specification<String>() {
            @Override
            public Predicate toPredicate(Root<String> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate p = cb.conjunction();
                if (getOperation().equals("EQ")) {
                    p = cb.and(p, cb.equal(root.get(attributeName), getValue()));
                } else if (getOperation().equals("LIKE")) {
                    p = cb.and(p, cb.like(root.get(attributeName), "%" + getValue() + "%"));
                } else if (getOperation().equals("STARTS_WITH")) {
                    p = cb.and(p, cb.like(root.get(attributeName), getValue() + "%"));
                } else if (getOperation().equals("ENDS_WITH")) {
                    p = cb.and(p, cb.like(root.get(attributeName), "%" + getValue()));
                }
                return p;
            }
        };
    }


}
