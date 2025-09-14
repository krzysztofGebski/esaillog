package com.esaillog.cruise;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record CruiseDto(
        String id,
        @NotBlank(message = "Name cannot be blank")
        String name,
        Set<String> participantsIDs,
        Set<String> visitedPortsIDs,
        @NotBlank(message = "Sailboat ID cannot be blank")
        String sailboatID,
        @NotNull(message = "Skipper ID cannot be null")
        String skipperID
) {
}
