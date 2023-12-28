package com.esaillog.common;

import java.util.List;

public record ErrorResponse(String message, List<ValidationError> validationErrors) {
}
