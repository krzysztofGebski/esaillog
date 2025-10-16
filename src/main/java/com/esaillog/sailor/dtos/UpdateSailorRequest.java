package com.esaillog.sailor.dtos;

import jakarta.validation.constraints.Email;

public record UpdateSailorRequest(
        String firstName,
        String lastName,
        @Email(message = "Email should be valid") String email
) {
}
