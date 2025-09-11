package com.esaillog.sailor;

import java.util.Set;

public record SailorDto(
        String id,
        String firstName,
        String lastName,
        String email,
        Set<String> cruisesIDs
) {
}
