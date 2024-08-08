package com.michelesalvucci.prenations.service.filter;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.Objects;
import org.springframework.data.jpa.domain.Specification;

public abstract class PNStandardSpecification {

    @SuppressWarnings("unchecked")
    public static <T, E> Specification<T> standardSpecification(E filters) {
        Specification<T> spec = Specification.where(null);

        for (java.lang.reflect.Field field : filters.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object value = field.get(filters);
                if (Objects.nonNull(value)) {
                    if (value instanceof PNFilterField) {
                        PNFilterField<?> filterField = (PNFilterField<?>) value;
                        if (filterField.getValue() instanceof String) {
                            spec = spec.and(stringSpecification(field.getName(), (PNFilterField<String>) filterField));
                        } else if (filterField.getValue() instanceof Boolean) {
                            spec = spec.and(booleanSpecification(field.getName(), (PNFilterField<Boolean>) filterField));
                        } else if (filterField.getValue() instanceof Long) {
                            spec = spec.and(longSpecification(field.getName(), (PNFilterField<Long>) filterField));
                        }
                    }
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return spec;
    }

    public static <T> Specification<T> stringSpecification(String attributeName, PNFilterField<String> filterField) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate p = cb.conjunction();
                if (Objects.nonNull(filterField)) {
                    if (filterField.getOperation().equals("EQ")) {
                        p = cb.and(p, cb.equal(root.get(attributeName), filterField.getValue()));
                    } else if (filterField.getOperation().equals("LIKE")) {
                        p = cb.and(p, cb.like(root.get(attributeName), "%" + filterField.getValue() + "%"));
                    } else if (filterField.getOperation().equals("STARTS_WITH")) {
                        p = cb.and(p, cb.like(root.get(attributeName), filterField.getValue() + "%"));
                    } else if (filterField.getOperation().equals("ENDS_WITH")) {
                        p = cb.and(p, cb.like(root.get(attributeName), "%" + filterField.getValue()));
                    }
                }
                return p;
            }
        };
    }

    public static <T> Specification<T> booleanSpecification(String attributeName, PNFilterField<Boolean> filterField) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate p = cb.conjunction();
                if (Objects.nonNull(filterField)) {
                    p = cb.and(p, cb.equal(root.get(attributeName), filterField.getValue()));
                }
                return p;
            }
        };
    }

    public static <T> Specification<T> longSpecification(String attributeName, PNFilterField<Long> filterField) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate p = cb.conjunction();
                if (Objects.nonNull(filterField)) {
                    p = cb.and(p, cb.equal(root.get(attributeName), filterField.getValue()));
                }
                return p;
            }
        };
    }
}
