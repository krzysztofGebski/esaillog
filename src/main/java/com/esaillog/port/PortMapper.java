package com.esaillog.port;

import com.esaillog.cruise.CruiseMapper;
import com.esaillog.sailboat.SailboatMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortMapper {
    private final SailboatMapper sailboatMapper;
    private final CruiseMapper cruiseMapper;

    public PortDto toPortDto(Port port) {
        return new PortDto(
                port.getId().toString(),
                port.getName(),
                port.getDescription(),
                port.getSailboats().stream().map(sailboatMapper::toSailboatDto).collect(Collectors.toSet()),
                port.getCruises().stream().map(cruiseMapper::toCruiseDto).collect(Collectors.toSet())
                );
    }

    public Port toPort(PortDto portDto) {
        return new Port(
                (portDto.id() != null) ? UUID.fromString(portDto.id()) : null,
                portDto.name(),
                portDto.description(),
                portDto.sailboats().stream().map(sailboatMapper::toSailboat).collect(Collectors.toSet()),
                portDto.cruises().stream().map(cruiseMapper::toCruise).collect(Collectors.toSet())
                );
    }
}
