package com.esaillog.cruise;

import java.util.Set;

public record CruiseDto(
        String id,
        String name,
        Set<String> participantsIDs,
        Set<String> visitedPortsIDs,
        String sailboatID
) {
}
