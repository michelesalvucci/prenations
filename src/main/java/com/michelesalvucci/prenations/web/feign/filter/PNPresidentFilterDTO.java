package com.michelesalvucci.prenations.web.feign.filter;

import com.michelesalvucci.prenations.service.filter.field.PNLongFilterField;
import com.michelesalvucci.prenations.service.filter.field.PNStringFilterField;

import lombok.Data;

@Data
public class PNPresidentFilterDTO {

    private PNStringFilterField name;
    private PNStringFilterField secondName;
    private PNLongFilterField partyId;
}
