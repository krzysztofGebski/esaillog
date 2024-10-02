package com.esaillog.sailor;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SailorMapper {
    public SailorDto toSailorDto(Sailor sailor) {
        return new SailorDto(sailor.getId().toString(), sailor.getFirstName(), sailor.getLastName(), sailor.getEmail());
    }

    public Sailor toSailor(SailorDto sailorDto) {
        UUID uuid = getUuid(sailorDto.id());
        return new Sailor(uuid, sailorDto.firstName(), sailorDto.lastName(), sailorDto.email());
    }

    private UUID getUuid(String id) {
        return (id != null) ? UUID.fromString(id) : UUID.randomUUID();
    }
}

