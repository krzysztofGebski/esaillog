package com.esaillog.sailor.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record UpdateSailorRequest(
        @Pattern(regexp = ".*\\S.*", message = "The field must not be blank or contain only whitespace.") String firstName,
        @Pattern(regexp = ".*\\S.*", message = "The field must not be blank or contain only whitespace.") String lastName,
        @Email(message = "Email should be valid") String email
) {
}
