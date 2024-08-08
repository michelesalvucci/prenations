package com.michelesalvucci.prenations.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PNService<T, D> {

    D save(D cityDTO);

    Optional<D> partialUpdate(D dto);

    default List<D> findAll() {
        return new ArrayList<D>();
    }

    default <F> Page<D> findAll(F filters, Pageable pageable) {
        return Page.empty();
    }

    Optional<D> findOne(T id);

    void delete(T id);

}
