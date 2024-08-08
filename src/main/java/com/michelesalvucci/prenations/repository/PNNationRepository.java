package com.michelesalvucci.prenations.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.michelesalvucci.prenations.domain.PNNation;

@SuppressWarnings("unused")
@Repository
public interface PNNationRepository extends JpaRepository<PNNation, Long>, JpaSpecificationExecutor<PNNation> {}

