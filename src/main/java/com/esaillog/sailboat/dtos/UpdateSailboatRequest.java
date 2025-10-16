package com.esaillog.sailboat.dtos;

import com.esaillog.sailboat.SailboatType;
import com.esaillog.validation.ValueOfEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record UpdateSailboatRequest(
        @NotBlank(message = "Name cannot be blank") String name,
        @NotBlank(message = "Register number cannot be blank") String registerNumber,
        @ValueOfEnum(enumClass = SailboatType.class, message = "Invalid sailboat type") String type,
        @Positive Double lengthInFeet,
        @Positive Double engineKW
) {
}
