package com.esaillog.port.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreatePortRequest(
        @NotBlank(message = "Name cannot be blank") String name,
        String description
) {
}
