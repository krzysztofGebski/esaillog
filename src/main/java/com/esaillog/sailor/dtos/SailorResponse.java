package com.esaillog.sailor.dtos;

import java.util.Set;
import java.util.UUID;

public record SailorResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        Set<UUID> cruisesIds,
        Set<UUID> skipperedCruisesIds,
        String updatedAt
) {
}
