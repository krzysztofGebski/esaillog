package com.esaillog.sailboat;

import java.util.Set;

public record SailboatDto(
        String id,
        String name,
        String registerNumber,
        String type,
        String homePortID,
        String length,
        String engineKW,
        Set<String> cruisesIDs
) {
}
