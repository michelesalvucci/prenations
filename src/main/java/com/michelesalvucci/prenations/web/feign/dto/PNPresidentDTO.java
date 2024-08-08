package com.michelesalvucci.prenations.web.feign.dto;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;

// TODO: should this DTO be in a common library package?
// the same for partyDTO and presidentFilterDTO
@Data
public class PNPresidentDTO implements Serializable {

    private Long id;
    private String name;
    private String secondName;
    private LocalDate birthDate;
    private Integer age;
    private Integer mandate;
    private PNPartyDTO party;
}
