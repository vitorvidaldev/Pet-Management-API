package dev.vitorvidal.petmanagementapi.infrastrucutre.exception;

import dev.vitorvidal.petmanagementapi.model.dto.ErrorResponseDTO;
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
    public ResponseEntity<ErrorResponseDTO> handleException(ResponseStatusException e) {

        ResponseEntity<ErrorResponseDTO> errorResponse = null;
        switch (HttpStatus.valueOf(e.getStatusCode().value())) {
            case NOT_FOUND ->
                    errorResponse = new ResponseEntity<>(new ErrorResponseDTO(NOT_FOUND.name(), e.getReason()), NOT_FOUND);
            case FORBIDDEN ->
                    errorResponse = new ResponseEntity<>(new ErrorResponseDTO(FORBIDDEN.name(), e.getReason()), FORBIDDEN);
            default ->
                    new ResponseEntity<>(new ErrorResponseDTO(HttpStatus.BAD_REQUEST.name(), e.getReason()), HttpStatus.BAD_REQUEST);
        }
        return errorResponse;
    }
}
