package com.esaillog.sailboat.dtos;

import java.util.UUID;

import com.esaillog.sailboat.SailboatType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateSailboatRequest(UUID id,
                @NotBlank(message = "Name cannot be blank") String name,
                @NotBlank(message = "Register number cannot be blank") String registerNumber,
                @NotNull(message = "Type cannot be null") SailboatType type,
                @NotNull(message = "Home port ID cannot be null") UUID homePortId,
                @NotNull(message = "Length cannot be null") @Positive(message = "Length must be a positive number") Double length,
                @NotNull(message = "Engine KW cannot be null") @Positive(message = "Engine KW must be a positive number") Double engineKW) {
}
