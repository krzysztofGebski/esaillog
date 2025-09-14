package com.esaillog.sailor;

import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.esaillog.common.EntityFinder;
import com.esaillog.cruise.CruiseRepository;
import com.esaillog.cruise.Cruise;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SailorMapper {
    private final CruiseRepository cruiseRepository;
    private final EntityFinder entityFinder;

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
                entityFinder.findAllByIds(cruiseRepository, sailorDto.cruisesIDs(), "Cruises", Cruise::getId),
                entityFinder.findAllByIds(cruiseRepository, sailorDto.skipperedCruisesIDs(), "Cruises", Cruise::getId)
        );
    }
}
