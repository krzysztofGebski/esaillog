package com.esaillog.port;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class PortRepository {
    private final List<Port> ports = new ArrayList<>();

    public List<Port> findAll() {
        return ports;
    }

    public Port findById(UUID id) {
        return ports.stream().filter(port -> port.getId().equals(id)).findFirst().orElseThrow();
    }

    public Port save(Port port) {
        Port savedPort = new Port(UUID.randomUUID(), port.getName(), port.getDescription());
        ports.add(savedPort);
        return savedPort;
    }

    public Port update(UUID id, Port port) {
        Port updatedPort = findById(id);
        updatedPort.setName(port.getName());
        updatedPort.setDescription(port.getDescription());
        return updatedPort;
    }

    public void delete(UUID id) {
        ports.removeIf(port -> port.getId().equals(id));
    }
}
