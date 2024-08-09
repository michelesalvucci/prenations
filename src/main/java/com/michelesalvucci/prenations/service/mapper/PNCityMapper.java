package com.michelesalvucci.prenations.service.mapper;

import com.michelesalvucci.prenations.domain.PNCity;
import com.michelesalvucci.prenations.service.dto.PNCityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PRENCity} and its DTO {@link PNCityDTO}.
 */
@Mapper(componentModel = "spring", uses = { PNNationMapper.class })
public interface PNCityMapper extends EntityMapper<PNCityDTO, PNCity> {

    @Mapping(target = "nationId", source = "nation.id") // sample: not necessary for the readonly field on the entity
    @Mapping(target = "nation.runtimePopulation", ignore = true)
    PNCityDTO toDto(PNCity s);

    @Mapping(target = "nation.id", source = "nationId") // sample: not necessary for the readonly field on the entity
    @Mapping(target = "nation", ignore = true)
    PNCity toEntity(PNCityDTO cityDTO);
}
