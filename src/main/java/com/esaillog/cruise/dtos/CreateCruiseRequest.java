package com.esaillog.cruise.dtos;

import java.util.Set;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCruiseRequest(
        @NotBlank(message = "Name cannot be blank")
        String name,
        Set<UUID> participantsIds,
        @NotNull(message = "Start port ID cannot be null")
        UUID startPortId,
        @NotNull(message = "End port ID cannot be null")
        UUID endPortId,
        Set<UUID> visitedPortsIds,
        @NotNull(message = "Sailboat ID cannot be null")
        UUID sailboatId,
        @NotNull(message = "Skipper ID cannot be null")
        UUID skipperId) {

}
