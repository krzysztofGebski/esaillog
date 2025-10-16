package com.esaillog.port.dtos;

import jakarta.validation.constraints.NotBlank;

public record UpdatePortRequest(
        @NotBlank(message = "Name cannot be blank") String name,
        String description
) {
}
