package com.esaillog.port;

import java.util.Set;

public record PortDto(
        String id,
        String name,
        String description,
        Set<String> sailboatsIDs,
        Set<String> cruisesIDs
) {
}
