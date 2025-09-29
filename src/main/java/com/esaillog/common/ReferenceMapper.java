package com.esaillog.common;

import com.esaillog.port.Port;
import com.esaillog.port.PortRepository;
import com.esaillog.sailboat.Sailboat;
import com.esaillog.sailboat.SailboatRepository;
import com.esaillog.sailor.Sailor;
import com.esaillog.sailor.SailorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReferenceMapper {
    private final SailorRepository sailorRepository;
    private final PortRepository portRepository;
    private final SailboatRepository sailboatRepository;

    public Set<Sailor> toSailors(Set<UUID> sailorIds) {
        if (sailorIds == null || sailorIds.isEmpty()) {
            return new HashSet<>();
        }
        return new HashSet<>(sailorRepository.findAllById(sailorIds));
    }

    public Set<Port> toPorts(Set<UUID> portIds) {
        if (portIds == null || portIds.isEmpty()) {
            return new HashSet<>();
        }
        return new HashSet<>(portRepository.findAllById(portIds));
    }

    public Sailboat toSailboat(UUID sailboatId) {
        return sailboatRepository.findById(sailboatId).orElse(null);
    }

    public Sailor toSailor(UUID sailorId) {
        return sailorRepository.findById(sailorId).orElse(null);
    }
}
