package com.faithjoyfundation.autopilotapi.v1.exceptions;

import com.faithjoyfundation.autopilotapi.v1.exceptions.error.ErrorMessage;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return Map.of("message", "Validation Failed", "errors", errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, Object> handleConstraintViolationExceptions(ConstraintViolationException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(error -> {
            errors.put(error.getPropertyPath().toString(), error.getMessage());
        });

        return Map.of("message", "Validation Failed", "errors", errors);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ErrorMessage badRequestException(BadRequestException ex) {
        return new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage());
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorMessage resourceNotFound(ResourceNotFoundException ex) {
        return new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage());
    }
}
