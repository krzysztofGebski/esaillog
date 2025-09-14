package com.esaillog.sailboat;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.esaillog.cruise.Cruise;
import com.esaillog.cruise.CruiseRepository;
import com.esaillog.error.EntityNotFoundException;
import com.esaillog.port.Port;
import com.esaillog.port.PortRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SailboatMapper {
    private final PortRepository portRepository;
    private final CruiseRepository cruiseRepository;

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
                getCruisesFromIds(sailboatDto.cruisesIDs())
        );
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

    private Port getPortFromId(String portID) {
        return portRepository.findById(UUID.fromString(portID))
                .orElseThrow(() -> new EntityNotFoundException("Port not found with id: " + portID));
    }
}
