package com.esaillog.sailor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record SailorDto(
        String id,
        @NotBlank(message = "First name cannot be blank")
        String firstName,
        @NotBlank(message = "Last name cannot be blank")
        String lastName,
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email should be valid")
        String email,
        Set<String> cruisesIDs,
        Set<String> skipperedCruisesIDs
) {
}
