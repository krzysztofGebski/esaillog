package com.esaillog.sailboat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Set;

public record SailboatDto(
        String id,
        @NotBlank(message = "Name cannot be blank")
        String name,
        @NotBlank(message = "Register number cannot be blank")
        String registerNumber,
        @NotNull(message = "Type cannot be null")
        SailboatType type,
        @NotBlank(message = "Home port ID cannot be blank")
        String homePortID,
        @NotBlank(message = "Length cannot be blank")
        @Pattern(regexp = "^\\d*\\.?\\d+$", message = "Length must be a valid number")
        String length,
        @NotBlank(message = "Engine KW cannot be blank")
        @Pattern(regexp = "^\\d*\\.?\\d+$", message = "Engine KW must be a valid number")
        String engineKW,
        Set<String> cruisesIDs
) {
}
