package dev.vitorvidal.petmanagementapi.infrastrucutre.exception;

import dev.vitorvidal.petmanagementapi.model.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(ResponseStatusException e) {

        ResponseEntity<ErrorResponseDTO> errorResponse = null;
        switch (e.getStatus()) {
            case NOT_FOUND -> errorResponse = new ResponseEntity<>(new ErrorResponseDTO(
                    HttpStatus.NOT_FOUND.name(),
                    e.getReason()
            ), HttpStatus.NOT_FOUND);
            case FORBIDDEN -> errorResponse = new ResponseEntity<>(new ErrorResponseDTO(
                    HttpStatus.FORBIDDEN.name(),
                    e.getReason()
            ), HttpStatus.FORBIDDEN);
        }
        return errorResponse;
    }
}
