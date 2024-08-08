package com.michelesalvucci.prenations.service.filter;

import java.io.Serializable;

import lombok.Data;

@Data
public class PNCityFilterDTO implements Serializable {
    private PNFilterField<String> name;
    private PNFilterField<Long> nationId;
}
