package com.esaillog.sailboat;

import com.esaillog.port.PortDto;

public record SailboatDto(
        String id,
        String name,
        String registerNumber,
        String type,
        PortDto homePort,
        String length,
        String engineKW
) {
}
