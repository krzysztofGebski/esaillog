package com.esaillog.common;

import com.esaillog.sailor.SailorNotFoundException;
import com.esaillog.training.TrainingNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({SailorNotFoundException.class, TrainingNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNotFoundException(Exception exception) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage(), List.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        var fieldErrors = exception.getBindingResult().getFieldErrors();
        var validationMessages = fieldErrors.stream().map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage())).toList();
        return buildErrorResponse(HttpStatus.NOT_ACCEPTABLE, "Field validation errors", validationMessages);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message, List<ValidationError> validationErrors) {
        return ResponseEntity.status(status).body(new ErrorResponse(message, validationErrors));
    }
}
