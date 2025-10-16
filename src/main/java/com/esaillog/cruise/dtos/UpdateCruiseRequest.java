package com.esaillog.cruise.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;
import java.util.UUID;

public record UpdateCruiseRequest(
        @NotBlank(message = "Name cannot be blank") String name,
        Set<UUID> participantsIds,
        UUID startPortId,
        UUID endPortId,
        Set<UUID> visitedPortsIds,
        UUID sailboatId,
        UUID skipperId
) {

}
