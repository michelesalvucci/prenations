package com.michelesalvucci.prenations.web.feign.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class PNPartyDTO implements Serializable {

    private Long id;
    private String name;
    private Integer yearOfFoundation;
}
