package com.michelesalvucci.prenations.service.mapper;

import com.michelesalvucci.prenations.domain.PNCity;
import com.michelesalvucci.prenations.domain.PNNation;
import com.michelesalvucci.prenations.service.dto.PNNationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PRENNation} and its DTO {@link PNNationDTO}.
 */
@Mapper(componentModel = "spring")
public interface PNNationMapper extends EntityMapper<PNNationDTO, PNNation> {
    @Mapping(target = "runtimePopulation", source = "nation", qualifiedByName = "runtimePopulation")
    PNNationDTO toDto(PNNation nation);

    @Mapping(target = "cities", ignore = true)
    PNNation toEntity(PNNationDTO nationDTO);

    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "cities", ignore = true)
    void partialUpdate(@MappingTarget PNNation entity, PNNationDTO dto);

    @Named("runtimePopulation")
    default Long runtimePopulation(PNNation nation) {
        Long sum = 0L;
        for (PNCity city : nation.getCities()) {
            sum += city.getPopulation();
        }
        return sum;
    }
}
