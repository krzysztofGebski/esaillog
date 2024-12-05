package com.esaillog.sailor;

import com.esaillog.cruise.CruiseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SailorMapper {
    private final CruiseMapper cruiseMapper;

    public SailorDto toSailorDto(Sailor sailor) {
        return new SailorDto(
                sailor.getId().toString(),
                sailor.getFirstName(),
                sailor.getLastName(),
                sailor.getEmail(),
                sailor.getCruises().stream().map(cruiseMapper::toCruiseDto).collect(Collectors.toSet()));
    }

    public Sailor toSailor(SailorDto sailorDto) {
        UUID uuid = getUuid(sailorDto.id());
        return new Sailor(
                uuid,
                sailorDto.firstName(),
                sailorDto.lastName(),
                sailorDto.email(),
                sailorDto.cruises().stream().map(cruiseMapper::toCruise).collect(Collectors.toSet())
        );
    }

    private UUID getUuid(String id) {
        return (id != null) ? UUID.fromString(id) : UUID.randomUUID();
    }
}

