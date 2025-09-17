package com.esaillog.port;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;
import java.util.Set;

public record PortDto(
        UUID id,
        @NotBlank(message = "Name cannot be blank")
        String name,
        String description,
        Set<UUID> sailboatsIds,
        Set<UUID> cruisesIds
) {
}