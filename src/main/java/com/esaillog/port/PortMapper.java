package com.esaillog.port;

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
                port.getSailboats().stream().map(sailboat -> sailboat.getId().toString()).collect(Collectors.toSet()),
                port.getCruises().stream().map(cruise -> cruise.getId().toString()).collect(Collectors.toSet())
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
        return sailboatsIDs.stream()
                .map(id -> sailboatRepository.findById(UUID.fromString(id))
                        .orElseThrow(() -> new EntityNotFoundException("Sailboat not found with id: " + id)))
                .collect(Collectors.toSet());
    }

    private Set<Cruise> getCruisesFromIds(Set<String> cruisesIDs) {
        return cruisesIDs.stream()
                .map(id -> cruiseRepository.findById(UUID.fromString(id))
                        .orElseThrow(() -> new EntityNotFoundException("Cruise not found with id: " + id)))
                .collect(Collectors.toSet());
    }
}
