package com.esaillog.sailor.dtos;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record SailorResponse(
        UUID id,
        String firstName,
        String lastName,
        String email,
        Set<UUID> cruiseIds,
        Set<UUID> skipperedCruiseIds,
        Instant updatedAt
) {
}
