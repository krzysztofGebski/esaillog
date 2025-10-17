package com.esaillog.common;

import com.esaillog.error.ResourceNotFoundException;
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

    /**
     * Converts a set of Sailor UUIDs to a set of Sailor objects.
     *
     * @param sailorIds A set of UUIDs representing the sailors.
     * @return A set of Sailor objects.
     */
    public Set<Sailor> toSailors(Set<UUID> sailorIds) {
        if (sailorIds == null || sailorIds.isEmpty()) {
            return new HashSet<>();
        }
        return new HashSet<>(sailorRepository.findAllById(sailorIds));
    }

    /**
     * Converts a set of Port UUIDs to a set of Port objects.
     *
     * @param portIds A set of UUIDs representing the ports.
     * @return A set of Port objects.
     */
    public Set<Port> toPorts(Set<UUID> portIds) {
        if (portIds == null || portIds.isEmpty()) {
            return new HashSet<>();
        }
        return new HashSet<>(portRepository.findAllById(portIds));
    }

    /**
     * Converts a Port UUID to a Port object.
     *
     * @param portId A UUID representing the port.
     * @return A Port object.
     * @throws ResourceNotFoundException if the port is not found.
     */
    public Port toPort(UUID portId) {
        if (portId == null) {
            return null;
        }
        return portRepository.findById(portId).orElseThrow(() -> new ResourceNotFoundException("Port", "id", portId));
    }

    /**
     * Converts a Sailboat UUID to a Sailboat object.
     *
     * @param sailboatId A UUID representing the sailboat.
     * @return A Sailboat object.
     * @throws ResourceNotFoundException if the sailboat is not found.
     */
    public Sailboat toSailboat(UUID sailboatId) {
        if (sailboatId == null) {
            return null;
        }
        return sailboatRepository.findById(sailboatId).orElseThrow(() -> new ResourceNotFoundException("Sailboat", "id", sailboatId));
    }

    /**
     * Converts a Sailor UUID to a Sailor object.
     *
     * @param sailorId A UUID representing the sailor.
     * @return A Sailor object.
     * @throws ResourceNotFoundException if the sailor is not found.
     */
    public Sailor toSailor(UUID sailorId) {
        if (sailorId == null) {
            return null;
        }
        return sailorRepository.findById(sailorId).orElseThrow(() -> new ResourceNotFoundException("Sailor", "id", sailorId));
    }
}
