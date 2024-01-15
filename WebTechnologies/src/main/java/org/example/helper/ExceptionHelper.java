package org.example.helper;

import org.example.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
public class ExceptionHelper {

    @ExceptionHandler({UserNotFound.class, ArticleNotFound.class})
    public ResponseEntity<ErrorResponse> handle(final BaseRuntimeException exception) {
        final var response = new ErrorResponse(NOT_FOUND.value(), NOT_FOUND.getReasonPhrase(), exception.getMessage());
        return ResponseEntity.status(NOT_FOUND).body(response);
    }

    @ExceptionHandler({Unauthorized.class})
    public ResponseEntity<ErrorResponse> handle2(final BaseRuntimeException exception) {
        final var response = new ErrorResponse(UNAUTHORIZED.value(), UNAUTHORIZED.getReasonPhrase(), exception.getMessage());
        return ResponseEntity.status(UNAUTHORIZED).body(response);
    }
}
