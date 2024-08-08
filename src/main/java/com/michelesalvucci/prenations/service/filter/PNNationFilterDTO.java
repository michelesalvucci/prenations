package com.michelesalvucci.prenations.service.filter;

import java.io.Serializable;

import com.michelesalvucci.prenations.service.filter.field.PNBooleanFilterField;
import com.michelesalvucci.prenations.service.filter.field.PNStringFilterField;

import lombok.Data;

@Data
public class PNNationFilterDTO implements Serializable {
    private PNStringFilterField name;
    private PNBooleanFilterField valid;
}
