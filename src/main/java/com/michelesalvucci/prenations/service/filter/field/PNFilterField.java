package com.michelesalvucci.prenations.service.filter.field;

import java.io.Serializable;

import org.springframework.data.jpa.domain.Specification;

import lombok.Data;

@Data
public abstract class PNFilterField<T> implements Serializable {

    private T value;

    private String operation;

    public abstract Class<T> getType();

    public abstract Specification<T> createSpecification(String attributeName);
}
