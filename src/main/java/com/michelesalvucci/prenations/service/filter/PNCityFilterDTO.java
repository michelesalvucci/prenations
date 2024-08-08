package com.michelesalvucci.prenations.service.filter;

import java.io.Serializable;

import com.michelesalvucci.prenations.service.filter.field.PNLongFilterField;
import com.michelesalvucci.prenations.service.filter.field.PNStringFilterField;

import lombok.Data;

@Data
public class PNCityFilterDTO implements Serializable {
    private PNStringFilterField name;
    private PNLongFilterField nationId;
}
