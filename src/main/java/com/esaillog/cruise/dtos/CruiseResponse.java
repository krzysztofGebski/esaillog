package com.esaillog.cruise.dtos;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record CruiseResponse(
        UUID id,
        String name,
        Set<UUID> participantsIds,
        UUID startPortId,
        UUID endPortId,
        Set<UUID> visitedPortsIds,
        UUID sailboatId,
        UUID skipperId,
        Instant updatedAt
) {
}
