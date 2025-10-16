package com.esaillog.port.dtos;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record PortResponse(
        UUID id,
        String name,
        String description,
        Set<UUID> sailboatsIds,
        Set<UUID> cruisesIds,
        Instant updatedAt
) {
}


