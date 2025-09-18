package com.esaillog.port.dtos;

import jakarta.validation.constraints.Pattern;

public record UpdatePortRequest(
        @Pattern(regexp = ".*\\S.*", message = "The field must not be blank or contain only whitespace.")
        String name,
        String description) {
}
