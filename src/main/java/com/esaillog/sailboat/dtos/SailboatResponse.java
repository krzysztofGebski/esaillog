package com.esaillog.sailboat.dtos;

import java.util.Set;
import java.util.UUID;

import com.esaillog.sailboat.SailboatType;

public record SailboatResponse(
        UUID id,
        String name,
        String registerNumber,
        SailboatType type,
        UUID homePortId,
        Double length,
        Double engineKW,
        Set<UUID> cruiseIds
) {
}
