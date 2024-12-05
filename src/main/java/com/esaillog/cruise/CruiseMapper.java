package com.esaillog.cruise;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CruiseMapper {
    public CruiseDto toCruiseDto(Cruise cruise) {
        return null;
    }

    public Cruise toCruise(CruiseDto cruiseDto) {
        return null;
    }

    private UUID getUuid(String id) {
        return (id != null) ? UUID.fromString(id) : UUID.randomUUID();
    }
}
