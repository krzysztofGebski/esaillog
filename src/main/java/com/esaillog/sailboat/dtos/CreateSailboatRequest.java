package com.esaillog.sailboat.dtos;

import com.esaillog.sailboat.SailboatType;
import com.esaillog.validation.ValueOfEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateSailboatRequest(
        @NotBlank(message = "Name cannot be blank") String name,
        @NotBlank(message = "Register number cannot be blank") String registerNumber,
        @NotBlank(message = "Type cannot be blank") @ValueOfEnum(enumClass = SailboatType.class, message = "Invalid sailboat type") String type,
        @NotNull(message = "Length cannot be null") @Positive(message = "Length must be a positive number") Double length,
        @NotNull(message = "Engine KW cannot be null") @Positive(message = "Engine KW must be a positive number") Double engineKW
) {
}
