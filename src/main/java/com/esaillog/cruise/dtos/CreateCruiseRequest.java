package com.esaillog.cruise.dtos;

import java.util.Set;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCruiseRequest(
        UUID id,
        @NotBlank(message = "Name cannot be blank")
        String name,
        Set<UUID> participantsIds,
        @NotBlank(message = "Start port ID cannot be blank")
        UUID startPortId,
        @NotBlank(message = "End port ID cannot be blank")
        UUID endPortId,
        Set<UUID> visitedPortsIds,
        @NotBlank(message = "Sailboat ID cannot be blank")
        UUID sailboatId,
        @NotNull(message = "Skipper ID cannot be null")
        UUID skipperId) {

}
