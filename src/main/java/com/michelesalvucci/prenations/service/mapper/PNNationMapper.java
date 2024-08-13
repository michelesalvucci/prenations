package com.michelesalvucci.prenations.service.mapper;

import com.michelesalvucci.prenations.domain.PNNation;
import com.michelesalvucci.prenations.repository.PNCityRepository;
import com.michelesalvucci.prenations.service.dto.PNNationDTO;

import lombok.extern.slf4j.Slf4j;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper for the entity {@link PRENNation} and its DTO {@link PNNationDTO}.
 */
@Mapper(componentModel = "spring")
@Slf4j
public abstract class PNNationMapper implements EntityMapper<PNNationDTO, PNNation> {
    
    @Autowired
    private PNCityRepository cityRepository;

    @Mapping(target = "runtimePopulation", source = "nation", qualifiedByName = "runtimePopulation")
    public abstract PNNationDTO toDto(PNNation nation);

    @Mapping(target = "cities", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    public abstract PNNation toEntity(PNNationDTO nationDTO);

    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "cities", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    public abstract void partialUpdate(@MappingTarget PNNation entity, PNNationDTO dto);

    /*
     * Many runtime parameters can be streamlined either by quering directly these aggregate attributes
     * or by making a View. This can lead to a more performant and readable code.
     * However, this is a sample of how to use a custom method to calculate a derived attribute.
     */
    @Named("runtimePopulation")
    protected Long runtimePopulation(PNNation nation) {
        return cityRepository.sumPopulationWhereNation(nation.getId());
    }
}
