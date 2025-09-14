package com.esaillog.sailor;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.esaillog.cruise.Cruise;
import com.esaillog.cruise.CruiseRepository;
import com.esaillog.error.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SailorMapper {
    private final CruiseRepository cruiseRepository;

    public SailorDto toSailorDto(Sailor sailor) {
        return new SailorDto(
                sailor.getId().toString(),
                sailor.getFirstName(),
                sailor.getLastName(),
                sailor.getEmail(),
                (sailor.getCruises() != null) ? sailor.getCruises().stream()
                        .map(cruise -> cruise.getId().toString())
                        .collect(Collectors.toSet()) : Collections.emptySet(),
                (sailor.getSkipperedCruises() != null) ? sailor.getSkipperedCruises().stream()
                        .map(cruise -> cruise.getId().toString())
                        .collect(Collectors.toSet()) : Collections.emptySet());
    }

    public Sailor toSailor(SailorDto sailorDto) {
        return new Sailor(
                (sailorDto.id() != null) ? UUID.fromString(sailorDto.id()) : null,
                sailorDto.firstName(),
                sailorDto.lastName(),
                sailorDto.email(),
                getCruisesFromIds(sailorDto.cruisesIDs()),
                getCruisesFromIds(sailorDto.skipperedCruisesIDs())
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
}
