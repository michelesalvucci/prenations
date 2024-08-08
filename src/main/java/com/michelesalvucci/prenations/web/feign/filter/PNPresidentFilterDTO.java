package com.michelesalvucci.prenations.web.feign.filter;

import com.michelesalvucci.prenations.service.filter.PNFilterField;
import lombok.Data;

@Data
public class PNPresidentFilterDTO {

    private PNFilterField<String> name;
    private PNFilterField<String> secondName;
    private PNFilterField<Long> partyId;
}
