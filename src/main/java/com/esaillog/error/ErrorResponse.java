package com.esaillog.error;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.Map;

/**
 * Represents a standardized error response body for the API.
 * This record is used to provide consistent, structured error information to clients.
 * The {@code validationErrors} field is only included in the JSON response when it is not null.
 *
 * @param timestamp        The exact time when the error occurred.
 * @param status           The HTTP status code.
 * @param error            The HTTP status reason phrase (e.g., "Not Found", "Bad Request").
 * @param message          A human-readable message providing more details about the error.
 * @param path             The request URI path that resulted in the error.
 * @param validationErrors A map of field-specific validation errors, present only for validation failures.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> validationErrors
) {
    /**
     * Convenience constructor for creating an ErrorResponse without validation errors.
     */
    public ErrorResponse(Instant timestamp, int status, String error, String message, String path) {
        this(timestamp, status, error, message, path, null);
    }
}
