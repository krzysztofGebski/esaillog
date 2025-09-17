package com.esaillog.sailor.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateSailorRequest(
        @NotBlank(message = "First name cannot be blank")
        String firstName,
        @NotBlank(message = "Last name cannot be blank")
        String lastName,
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email should be valid")
        String email) {
}
