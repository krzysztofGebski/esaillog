package com.esaillog.cruise;

import org.springframework.stereotype.Service;

import com.esaillog.port.PortMapper;
import com.esaillog.sailboat.SailboatMapper;
import com.esaillog.sailor.SailorMapper;

import lombok.RequiredArgsConstructor;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CruiseMapper {
    private final SailorMapper sailorMapper;
    private final PortMapper portMapper;
    private final SailboatMapper sailboatMapper;

    public CruiseDto toCruiseDto(Cruise cruise) {
        return new CruiseDto(
                cruise.getId().toString(),
                cruise.getName(),
                cruise.getParticipants().stream().map(sailorMapper::toSailorDto).collect(Collectors.toSet()),
                cruise.getVisitedPorts().stream().map(portMapper::toPortDto).collect(Collectors.toSet()),
                sailboatMapper.toSailboatDto(cruise.getSailboat()));

    }

    public Cruise toCruise(CruiseDto cruiseDto) {
        return new Cruise(
                (cruiseDto.id() != null) ? UUID.fromString(cruiseDto.id()) : null,
                cruiseDto.name(),
                cruiseDto.participants().stream().map(sailorMapper::toSailor).collect(Collectors.toSet()),
                cruiseDto.visitedPorts().stream().map(portMapper::toPort).collect(Collectors.toSet()),
                sailboatMapper.toSailboat(cruiseDto.sailboat()));
    }
}
