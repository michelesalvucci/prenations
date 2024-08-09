package com.michelesalvucci.prenations.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.michelesalvucci.prenations.domain.PNCity;

@SuppressWarnings("unused")
@Repository
public interface PNCityRepository extends JpaRepository<PNCity, Long>, JpaSpecificationExecutor<PNCity> {

    @Query("SELECT SUM(city.population) FROM PNCity city WHERE city.nationId = :nationId")
    Long sumPopulationWhereNation(Long nationId);
}
