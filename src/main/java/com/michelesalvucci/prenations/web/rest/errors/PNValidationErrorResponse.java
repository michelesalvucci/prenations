package com.michelesalvucci.prenations.web.rest.errors;

import lombok.Data;

@Data
public class PNValidationErrorResponse {

    private String message;
    private String fieldMessages;
}
