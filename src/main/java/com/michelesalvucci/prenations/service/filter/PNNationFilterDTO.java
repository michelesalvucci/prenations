package com.michelesalvucci.prenations.service.filter;

import java.io.Serializable;

import lombok.Data;

@Data
public class PNNationFilterDTO implements Serializable {

    private PNFilterField<String> name;
    private PNFilterField<Boolean> valid;
}
