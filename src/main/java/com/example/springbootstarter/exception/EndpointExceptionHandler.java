package com.example.springbootstarter.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class EndpointExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEntityNotFoundException(Exception e) {
        return handleException(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEmailNotFoundException(Exception e) {
        return handleException(e, HttpStatus.NOT_FOUND, "email");
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyExistsException(Exception e) {
        return handleException(e, HttpStatus.CONFLICT, "email");
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<Map<String, String>> handleInvalidPasswordException(Exception e) {
        return handleException(e, HttpStatus.UNAUTHORIZED, "password");
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentialsException(Exception e) {
        return handleException(e, HttpStatus.UNAUTHORIZED, "token");
    }

    @ExceptionHandler(EmailUnverifiedException.class)
    public ResponseEntity<Map<String, String>> handleEmailUnverifiedException(Exception e) {
        return handleException(e, HttpStatus.UNAUTHORIZED, "email");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAccessDeniedException(Exception e) {
        return handleException(e, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception e) {
        return handleException(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Map<String, String>> handleException(Exception e, HttpStatus status, String field) {
        Map<String, String> body = Map.of(field, e.getMessage());
        return ResponseEntity.status(status).body(body);
    }

    private ResponseEntity<Map<String, String>> handleException(Exception e, HttpStatus status) {
        return handleException(e, status, "message");
    }
}
