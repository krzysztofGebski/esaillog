package com.esaillog.sailboat;

import com.esaillog.cruise.CruiseMapper;
import com.esaillog.port.PortMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SailboatMapper {
    private final PortMapper portMapper;
    private final CruiseMapper cruiseMapper;

    public SailboatDto toSailboatDto(Sailboat sailboat) {
        return new SailboatDto(
                sailboat.getId().toString(),
                sailboat.getName(),
                sailboat.getRegisterNumber(),
                sailboat.getType(),
                portMapper.toPortDto(sailboat.getHomePort()),
                String.valueOf(sailboat.getLength()),
                String.valueOf(sailboat.getEngineKW()),
                sailboat.getCruises().stream().map(cruiseMapper::toCruiseDto).collect(Collectors.toSet())
        );
    }

    public Sailboat toSailboat(SailboatDto sailboatDto) {
        UUID uuid = getUuid(sailboatDto.id());
        return new Sailboat(
                uuid,
                sailboatDto.name(),
                sailboatDto.registerNumber(),
                sailboatDto.type(),
                portMapper.toPort(sailboatDto.homePort()),
                Double.parseDouble(sailboatDto.length()),
                Double.parseDouble(sailboatDto.engineKW()),
                sailboatDto.cruises().stream().map(cruiseMapper::toCruise).collect(Collectors.toSet())
                );
    }

    private UUID getUuid(String id) {
        return (id != null) ? UUID.fromString(id) : UUID.randomUUID();
    }
}
