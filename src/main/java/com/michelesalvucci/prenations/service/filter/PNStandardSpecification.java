package com.michelesalvucci.prenations.service.filter;

import lombok.extern.slf4j.Slf4j;
import java.util.Objects;
import org.springframework.data.jpa.domain.Specification;

import com.michelesalvucci.prenations.service.filter.field.PNFilterField;

@Slf4j
public abstract class PNStandardSpecification {

    @SuppressWarnings("unchecked")
    public static <T, E> Specification<T> standardSpecification(E filters) {
        Specification<T> spec = Specification.where(null);

        for (java.lang.reflect.Field field : filters.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object value = field.get(filters);
                if (Objects.nonNull(value)) {
                    PNFilterField<?> filterField = (PNFilterField<?>) value;
                    spec = spec.and((Specification<T>) filterField.createSpecification(field.getName()));
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return spec;
    }
}
