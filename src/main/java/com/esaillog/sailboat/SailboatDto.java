package com.esaillog.sailboat;

import com.esaillog.cruise.CruiseDto;
import com.esaillog.port.PortDto;

import java.util.Set;

public record SailboatDto(
        String id,
        String name,
        String registerNumber,
        String type,
        PortDto homePort,
        String length,
        String engineKW,
        Set<CruiseDto> cruises
) {
}
