package com.esaillog.sailboat;

import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.esaillog.common.EntityFinder;
import com.esaillog.cruise.CruiseRepository;
import com.esaillog.cruise.Cruise;
import com.esaillog.port.Port;
import com.esaillog.port.PortRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SailboatMapper {
    private final PortRepository portRepository;
    private final CruiseRepository cruiseRepository;
    private final EntityFinder entityFinder;

    public SailboatDto toSailboatDto(Sailboat sailboat) {
        return new SailboatDto(
                sailboat.getId().toString(),
                sailboat.getName(),
                sailboat.getRegisterNumber(),
                sailboat.getType(),
                sailboat.getHomePort().getId().toString(),
                String.valueOf(sailboat.getLength()),
                String.valueOf(sailboat.getEngineKW()),
                (sailboat.getCruises() != null) ? sailboat.getCruises().stream()
                        .map(cruise -> cruise.getId().toString())
                        .collect(Collectors.toSet()) : Collections.emptySet()
        );
    }

    public Sailboat toSailboat(SailboatDto sailboatDto) {
        return new Sailboat(
                (sailboatDto.id() != null) ? UUID.fromString(sailboatDto.id()) : null,
                sailboatDto.name(),
                sailboatDto.registerNumber(),
                sailboatDto.type(),
                getPortFromId(sailboatDto.homePortID()),
                Double.parseDouble(sailboatDto.length()),
                Double.parseDouble(sailboatDto.engineKW()),
                entityFinder.findAllByIds(cruiseRepository, sailboatDto.cruisesIDs(), "Cruises", Cruise::getId)
        );
    }

    private Port getPortFromId(String portID) {
        return entityFinder.findById(portRepository, portID, "Port");
    }
}
