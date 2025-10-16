package com.esaillog.sailboat.dtos;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import com.esaillog.sailboat.SailboatType;

public record SailboatResponse(
        UUID id,
        String name,
        String registerNumber,
        SailboatType type,
        Double lengthInFeet,
        Double engineKW,
        Set<UUID> cruiseIds,
        Instant updatedAt
) {
}
