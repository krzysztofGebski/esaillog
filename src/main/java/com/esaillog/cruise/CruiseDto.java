package com.esaillog.cruise;

import com.esaillog.port.PortDto;
import com.esaillog.sailboat.SailboatDto;
import com.esaillog.sailor.SailorDto;

import java.util.Set;

public record CruiseDto(
        String id,
        String name,
        Set<SailorDto> participants,
        Set<PortDto> visitedPorts,
        SailboatDto sailboat
) {
}
