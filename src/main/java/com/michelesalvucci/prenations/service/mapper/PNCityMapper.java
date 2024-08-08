package com.michelesalvucci.prenations.service.mapper;

import com.michelesalvucci.prenations.domain.PNCity;
import com.michelesalvucci.prenations.service.dto.PNCityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PRENCity} and its DTO {@link PNCityDTO}.
 */
@Mapper(componentModel = "spring", uses = { PNNationMapper.class })
public interface PNCityMapper extends EntityMapper<PNCityDTO, PNCity> {

    @Mapping(target = "nationId", source = "nation.id") // esemplificativo: non necessario per il campo nationId sulla entity
    PNCityDTO toDto(PNCity s);

    @Mapping(target = "nation.id", source = "nationId") // esemplificativo: non necessario per il campo nationId sulla entity
    @Mapping(target = "nation", ignore = true)
    PNCity toEntity(PNCityDTO cityDTO);
}
