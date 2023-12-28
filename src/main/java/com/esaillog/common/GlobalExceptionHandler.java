package com.esaillog.common;

import com.esaillog.sailor.SailorNotFoundException;
import com.esaillog.training.TrainingNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({SailorNotFoundException.class, TrainingNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNotFoundException(Exception exception) {
        return buildErrorResponse(exception, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<String> buildErrorResponse(Exception exception, HttpStatus status) {
        return ResponseEntity
                .status(status)
                .body(exception.getMessage());
    }
}
