package com.esaillog.sailboat.dtos;

import java.util.UUID;

import com.esaillog.sailboat.SailboatType;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record UpdateSailboatRequest(
        @Pattern(regexp = ".*\\S.*", message = "The field must not be blank or contain only whitespace.") String name,
        @Pattern(regexp = ".*\\S.*", message = "The field must not be blank or contain only whitespace.") String registerNumber,
        @Pattern(regexp = ".*\\S.*", message = "The field must not be blank or contain only whitespace.") SailboatType type,
        @Pattern(regexp = ".*\\S.*", message = "The field must not be blank or contain only whitespace.") UUID homePortId,
        @Pattern(regexp = ".*\\d.*", message = "Length cannot be null") @Positive(message = "Length must be a positive number") Double length,
        @Pattern(regexp = ".*\\d.*", message = "Engine KW cannot be null") @Positive(message = "Engine KW must be a positive number") Double engineKW) {
}
