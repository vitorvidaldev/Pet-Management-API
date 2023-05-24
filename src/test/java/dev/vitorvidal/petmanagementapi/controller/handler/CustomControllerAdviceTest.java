import dev.vitorvidal.petmanagementapi.domain.model.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomControllerAdviceTest {

    @Test
    public void testHandleExceptionNotFound() {
        ResponseStatusException e = new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        CustomControllerAdvice advice = new CustomControllerAdvice();
        ResponseEntity<ErrorResponse> response = advice.handleException(e);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Not found", response.getBody().getMessage());
    }

    @Test
    public void testHandleExceptionForbidden() {
        ResponseStatusException e = new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
        CustomControllerAdvice advice = new CustomControllerAdvice();
        ResponseEntity<ErrorResponse> response = advice.handleException(e);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Forbidden", response.getBody().getMessage());
    }

    @Test
    public void testHandleExceptionBadRequest() {
        ResponseStatusException e = new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        CustomControllerAdvice advice = new CustomControllerAdvice();
        ResponseEntity<ErrorResponse> response = advice.handleException(e);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Bad request", response.getBody().getMessage());
    }
}