package com.esaillog.port;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.esaillog.cruise.Cruise;
import com.esaillog.cruise.CruiseRepository;
import com.esaillog.error.EntityNotFoundException;
import com.esaillog.sailboat.Sailboat;
import com.esaillog.sailboat.SailboatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PortMapper {
    private final SailboatRepository sailboatRepository;
    private final CruiseRepository cruiseRepository;

    public PortDto toPortDto(Port port) {
        return new PortDto(
                port.getId().toString(),
                port.getName(),
                port.getDescription(),
                (port.getSailboats() != null) ? port.getSailboats().stream()
                        .map(sailboat -> sailboat.getId().toString())
                        .collect(Collectors.toSet()) : Collections.emptySet(),
                (port.getCruises() != null) ? port.getCruises().stream()
                        .map(cruise -> cruise.getId().toString())
                        .collect(Collectors.toSet()) : Collections.emptySet()
        );
    }

    public Port toPort(PortDto portDto) {
        return new Port(
                (portDto.id() != null) ? UUID.fromString(portDto.id()) : null,
                portDto.name(),
                portDto.description(),
                getSailboatsFromIds(portDto.sailboatsIDs()),
                getCruisesFromIds(portDto.cruisesIDs())
        );
    }

    private Set<Sailboat> getSailboatsFromIds(Set<String> sailboatsIDs) {
        if (sailboatsIDs == null || sailboatsIDs.isEmpty()) {
            return Collections.emptySet();
        }
        Set<UUID> sailboatUuids = sailboatsIDs.stream().map(UUID::fromString).collect(Collectors.toSet());
        Set<Sailboat> sailboats = sailboatRepository.findAllById(sailboatUuids).stream().collect(Collectors.toSet());

        if (sailboats.size() != sailboatUuids.size()) {
            Set<UUID> foundSailboatIds = sailboats.stream().map(Sailboat::getId).collect(Collectors.toSet());
            sailboatUuids.removeAll(foundSailboatIds);
            throw new EntityNotFoundException("Sailboats not found with ids: " + sailboatUuids);
        }

        return sailboats;
    }

    private Set<Cruise> getCruisesFromIds(Set<String> cruisesIDs) {
        if (cruisesIDs == null || cruisesIDs.isEmpty()) {
            return Collections.emptySet();
        }
        Set<UUID> cruiseUuids = cruisesIDs.stream().map(UUID::fromString).collect(Collectors.toSet());
        Set<Cruise> cruises = cruiseRepository.findAllById(cruiseUuids).stream().collect(Collectors.toSet());

        if (cruises.size() != cruiseUuids.size()) {
            Set<UUID> foundCruiseIds = cruises.stream().map(Cruise::getId).collect(Collectors.toSet());
            cruiseUuids.removeAll(foundCruiseIds);
            throw new EntityNotFoundException("Cruises not found with ids: " + cruiseUuids);
        }

        return cruises;
    }
}
