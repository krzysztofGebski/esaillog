package com.esaillog.cruise;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.esaillog.error.EntityNotFoundException;
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

    public CruiseDto toCruiseDto(Cruise cruise) {
        return new CruiseDto(
                cruise.getId().toString(),
                cruise.getName(),
                cruise.getParticipants().stream().map(participant -> participant.getId().toString()).collect(Collectors.toSet()),
                cruise.getVisitedPorts().stream().map(visitedPort -> visitedPort.getId().toString()).collect(Collectors.toSet()),
                cruise.getSailboat().getId().toString()
                );

    }

    public Cruise toCruise(CruiseDto cruiseDto) {
        return new Cruise(
                (cruiseDto.id() != null) ? UUID.fromString(cruiseDto.id()) : null,
                cruiseDto.name(),
                getParticipantsFromIds(cruiseDto.participantsIDs()),
                getPortsFromIds(cruiseDto.visitedPortsIDs()),
                getSailboatFromId(cruiseDto.sailboatID())
                );
    }

    private final Set<Sailor> getParticipantsFromIds(Set<String> participantsIDs) {
        return participantsIDs.stream()
                .map(id -> sailorRepository.findById(UUID.fromString(id))
                        .orElseThrow(() -> new EntityNotFoundException("Sailor not found with id: " + id)))
                .collect(Collectors.toSet());
    }

    private Set<Port> getPortsFromIds(Set<String> portsIDs) {
        return portsIDs.stream()
                .map(id -> portRepository.findById(UUID.fromString(id))
                        .orElseThrow(() -> new EntityNotFoundException("Port not found with id: " + id)))
                .collect(Collectors.toSet());
    }

    private Sailboat getSailboatFromId(String sailboatID) {
        return sailboatRepository.findById(UUID.fromString(sailboatID))
                .orElseThrow(() -> new EntityNotFoundException("Sailboat not found with id: " + sailboatID));
    }
}
