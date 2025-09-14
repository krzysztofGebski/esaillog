package com.esaillog.port;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record PortDto(
        String id,
        @NotBlank(message = "Name cannot be blank")
        String name,
        String description,
        Set<String> sailboatsIDs,
        Set<String> cruisesIDs
) {
}