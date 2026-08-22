package com.fteodoro.webmonitor.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseError> resourceNotFoundHandler(
        ResourceNotFoundException exception
    ) {
        var response = new ResponseError(
            HttpStatus.NOT_FOUND.value(),
            exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<InvalidArgumentError> methodArgumentNotValidHandler(MethodArgumentNotValidException exception) {
        var invalidFields = exception
            .getFieldErrors()
            .stream()
            .collect(Collectors.toMap(
                FieldError::getField,
                FieldError::getDefaultMessage
            ));
        var error = new InvalidArgumentError(HttpStatus.BAD_REQUEST.value(), invalidFields);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
