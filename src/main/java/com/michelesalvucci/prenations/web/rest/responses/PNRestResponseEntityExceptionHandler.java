package com.michelesalvucci.prenations.web.rest.responses;

import jakarta.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class PNRestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatusCode status,
        WebRequest request
    ) {
        BindingResult result = ex.getBindingResult();
        PNValidationErrorResponse customValidationErrorResponse = new PNValidationErrorResponse();
        List<FieldError> fieldErrors = result.getFieldErrors();
        customValidationErrorResponse.setMessage("Invalid request fields");
        customValidationErrorResponse.setFieldMessages(
            fieldErrors.stream().map(f -> f.getDefaultMessage()).collect(Collectors.toList()).toString()
        );
        return new ResponseEntity<>(customValidationErrorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { ValidationException.class })
    protected ResponseEntity<PNValidationErrorResponse> validationException(ValidationException ex) {
        PNValidationErrorResponse customValidationErrorResponse = new PNValidationErrorResponse();
        customValidationErrorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(customValidationErrorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
