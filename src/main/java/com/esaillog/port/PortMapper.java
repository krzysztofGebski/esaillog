package com.esaillog.port;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PortMapper {
    public PortDto toPortDto(Port port) {
        return new PortDto(port.getId().toString(), port.getName(), port.getDescription());
    }

    public Port toPort(PortDto portDto) {
        UUID uuid = getUuid(portDto.id());
        return new Port(uuid, portDto.name(), portDto.description());
    }

    private UUID getUuid(String id) {
        return (id != null) ? UUID.fromString(id) : UUID.randomUUID();
    }
}
