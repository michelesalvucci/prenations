package com.michelesalvucci.prenations.web.rest.responses;

import lombok.Data;
import java.io.Serializable;

@Data
public class PNValidationErrorResponse implements Serializable {

    private String message;
    private String fieldMessages;
}
