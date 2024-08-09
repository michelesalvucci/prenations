package com.michelesalvucci.prenations.service.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PNCityDTO extends AbstractAuditingDTO {
    private Long id;
    
    @NotNull(message = "Name is required")
    private String name;
    
    @NotNull(message = "Population is required")
    @Min(value = 100, message = "Population must be more than 100")
    private Long population;
    
    @NotNull(message = "Nation is required")
    private Long nationId;
    
    private PNNationDTO nation;
}
