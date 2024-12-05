package com.esaillog.sailor;

import com.esaillog.cruise.CruiseDto;

import java.util.Set;

public record SailorDto(
        String id,
        String firstName,
        String lastName,
        String email,
        Set<CruiseDto> cruises
) {
}
