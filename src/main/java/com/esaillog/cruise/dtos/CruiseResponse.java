package com.esaillog.cruise.dtos;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record CruiseResponse(
        UUID id,
        String name,
        Set<UUID> participantIds,
        UUID startPortId,
        UUID endPortId,
        Set<UUID> visitedPortIds,
        UUID sailboatId,
        UUID skipperId,
        Instant updatedAt
) {
}
