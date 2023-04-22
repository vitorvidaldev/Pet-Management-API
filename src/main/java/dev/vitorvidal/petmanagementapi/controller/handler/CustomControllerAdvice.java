package dev.vitorvidal.petmanagementapi.controller.handler;

import dev.vitorvidal.petmanagementapi.domain.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleException(ResponseStatusException e) {

        ResponseEntity<ErrorResponse> errorResponse = null;
        switch (HttpStatus.valueOf(e.getStatusCode().value())) {
            case NOT_FOUND ->
                    errorResponse = new ResponseEntity<>(new ErrorResponse(NOT_FOUND.name(), e.getReason()), NOT_FOUND);
            case FORBIDDEN ->
                    errorResponse = new ResponseEntity<>(new ErrorResponse(FORBIDDEN.name(), e.getReason()), FORBIDDEN);
            default ->
                    new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.name(), e.getReason()), HttpStatus.BAD_REQUEST);
        }
        return errorResponse;
    }
}
