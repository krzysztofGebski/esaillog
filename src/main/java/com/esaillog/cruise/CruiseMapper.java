package com.esaillog.cruise;

import java.util.Collections;
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
                getParticipantsFromIds(cruiseDto.participantsIDs()),
                getPortsFromIds(cruiseDto.visitedPortsIDs()),
                getSailboatFromId(cruiseDto.sailboatID()),
                getSkipperFromId(cruiseDto.skipperID())
        );
    }

    private final Set<Sailor> getParticipantsFromIds(Set<String> participantsIDs) {
        if (participantsIDs == null || participantsIDs.isEmpty()) {
            return Collections.emptySet();
        }
        Set<UUID> participantUuids = participantsIDs.stream().map(UUID::fromString).collect(Collectors.toSet());
        Set<Sailor> participants = sailorRepository.findAllById(participantUuids).stream().collect(Collectors.toSet());

        if (participants.size() != participantUuids.size()) {
            Set<UUID> foundParticipantIds = participants.stream().map(Sailor::getId).collect(Collectors.toSet());
            participantUuids.removeAll(foundParticipantIds);
            throw new EntityNotFoundException("Sailors not found with ids: " + participantUuids);
        }

        return participants;
    }

    private Set<Port> getPortsFromIds(Set<String> portsIDs) {
        if (portsIDs == null || portsIDs.isEmpty()) {
            return Collections.emptySet();
        }
        Set<UUID> portUuids = portsIDs.stream().map(UUID::fromString).collect(Collectors.toSet());
        Set<Port> ports = portRepository.findAllById(portUuids).stream().collect(Collectors.toSet());

        if (ports.size() != portUuids.size()) {
            Set<UUID> foundPortIds = ports.stream().map(Port::getId).collect(Collectors.toSet());
            portUuids.removeAll(foundPortIds);
            throw new EntityNotFoundException("Ports not found with ids: " + portUuids);
        }

        return ports;
    }

    private Sailboat getSailboatFromId(String sailboatID) {
        return sailboatRepository.findById(UUID.fromString(sailboatID))
                .orElseThrow(() -> new EntityNotFoundException("Sailboat not found with id: " + sailboatID));
    }

    private Sailor getSkipperFromId(String skipperID) {
        return sailorRepository.findById(UUID.fromString(skipperID))
                .orElseThrow(() -> new EntityNotFoundException("Sailor (skipper) not found with id: " + skipperID));
    }
}
