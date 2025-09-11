package com.esaillog.sailor;

import com.esaillog.cruise.Cruise;
import com.esaillog.cruise.CruiseRepository;
import com.esaillog.error.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
                sailor.getCruises().stream().map(cruise -> cruise.getId().toString()).collect(Collectors.toSet()));
    }

    public Sailor toSailor(SailorDto sailorDto) {
        return new Sailor(
                (sailorDto.id() != null) ? UUID.fromString(sailorDto.id()) : null,
                sailorDto.firstName(),
                sailorDto.lastName(),
                sailorDto.email(),
                getCruisesFromIds(sailorDto.cruisesIDs())
        );
    }

    private Set<Cruise> getCruisesFromIds(Set<String> cruisesIDs) {
        return cruisesIDs.stream()
                .map(id -> cruiseRepository.findById(UUID.fromString(id))
                        .orElseThrow(() -> new EntityNotFoundException("Cruise not found with id: " + id)))
                .collect(Collectors.toSet());
    }
}
