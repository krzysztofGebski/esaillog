package com.esaillog.port;

import com.esaillog.cruise.CruiseDto;
import com.esaillog.sailboat.SailboatDto;

import java.util.Set;

public record PortDto(
        String id,
        String name,
        String description,
        Set<SailboatDto> sailboats,
        Set<CruiseDto> cruises
) {
}
