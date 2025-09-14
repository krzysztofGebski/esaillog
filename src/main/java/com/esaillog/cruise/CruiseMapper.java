package com.esaillog.cruise;

import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.esaillog.common.EntityFinder;
import com.esaillog.port.Port;
import com.esaillog.port.PortRepository;
import com.esaillog.sailboat.Sailboat;
import com.esaillog.sailboat.SailboatRepository;
import com.esaillog.sailor.Sailor;
import com.esaillog.sailor.SailorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CruiseMapper {
    private final SailboatRepository sailboatRepository;
    private final SailorRepository sailorRepository;
    private final PortRepository portRepository;
    private final EntityFinder entityFinder;

    public CruiseDto toCruiseDto(Cruise cruise) {
        return new CruiseDto(
                cruise.getId().toString(),
                cruise.getName(),
                (cruise.getParticipants() != null) ? cruise.getParticipants().stream()
                        .map(participant -> participant.getId().toString())
                        .collect(Collectors.toSet()) : Collections.emptySet(),
                (cruise.getVisitedPorts() != null) ? cruise.getVisitedPorts().stream()
                        .map(visitedPort -> visitedPort.getId().toString())
                        .collect(Collectors.toSet()) : Collections.emptySet(),
                cruise.getSailboat().getId().toString(),
                cruise.getSkipper().getId().toString()
        );

    }

    public Cruise toCruise(CruiseDto cruiseDto) {
        return new Cruise(
                (cruiseDto.id() != null) ? UUID.fromString(cruiseDto.id()) : null,
                cruiseDto.name(),
                entityFinder.findAllByIds(sailorRepository, cruiseDto.participantsIDs(), "Sailors", Sailor::getId),
                entityFinder.findAllByIds(portRepository, cruiseDto.visitedPortsIDs(), "Ports", Port::getId),
                getSailboatFromId(cruiseDto.sailboatID()),
                getSkipperFromId(cruiseDto.skipperID())
        );
    }

    private Sailboat getSailboatFromId(String sailboatID) {
        return entityFinder.findById(sailboatRepository, sailboatID, "Sailboat");
    }

    private Sailor getSkipperFromId(String skipperID) {
        return entityFinder.findById(sailorRepository, skipperID, "Sailor (skipper)");
    }
}
