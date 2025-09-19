package com.esaillog.cruise;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.esaillog.cruise.dtos.CruiseResponse;
import com.esaillog.port.Port;
import com.esaillog.sailor.Sailor;

@Mapper(componentModel = "spring")
public interface CruiseMapper {

    @Mapping(source = "participants", target = "participantsIds", qualifiedByName = "sailorsToIds")
    @Mapping(source = "visitedPorts", target = "visitedPortsIds", qualifiedByName = "portsToIds")
    @Mapping(source = "sailboat", target = "sailboatId", qualifiedByName = "sailboatToId")
    @Mapping(source = "skipper", target = "skipperId", qualifiedByName = "sailorToId")
    CruiseResponse toCruiseDto(Cruise cruise);

    @Mapping(target = "participants", ignore = true)
    @Mapping(target = "visitedPorts", ignore = true)
    @Mapping(target = "sailboat", ignore = true)
    @Mapping(target = "skipper", ignore = true)
    @Mapping(target = "id", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Cruise toCruise(CruiseResponse cruiseDto);

    @Named("sailorsToIds")
    default Set<UUID> sailorsToIds(Set<Sailor> sailors) {
        if (sailors == null) {
            return Set.of();
        }
        return sailors.stream()
                .map(Sailor::getId)
                .collect(Collectors.toSet());
    }

    @Named("sailorToId")
    default UUID sailorToId(Sailor sailor) {
        if (sailor == null) {
            return null;
        }
        return sailor.getId();
    }

    @Named("portsToIds")
    default Set<UUID> portsToIds(Set<Port> ports) {
        if (ports == null) {
            return Set.of();
        }
        return ports.stream()
                .map(Port::getId)
                .collect(Collectors.toSet());
    }

    @Named("sailboatToId")
    default UUID sailboatToId(com.esaillog.sailboat.Sailboat sailboat) {
        if (sailboat == null) {
            return null;
        }
        return sailboat.getId();
    }
}
