package com.michelesalvucci.prenations.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=false)
public class PNNationDTO extends AbstractAuditingDTO {
    private Long id;
    
    @Size(min = 2, max = 20, message = "Name must be at least 2 chars long and at most 20 chars long")
    private String name;

    @NotNull(message = "Valid field required")
    private Boolean valid;

    private Long runtimePopulation;
}
