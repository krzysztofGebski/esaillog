package com.esaillog.cruise;

import com.esaillog.common.ReferenceMapper;
import com.esaillog.cruise.dtos.CreateCruiseRequest;
import com.esaillog.cruise.dtos.CruiseResponse;
import com.esaillog.port.Port;
import com.esaillog.sailor.Sailor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = ReferenceMapper.class)
public interface CruiseMapper {

    @Mapping(source = "participants", target = "participantsIds", qualifiedByName = "sailorsToIds")
    @Mapping(source = "visitedPorts", target = "visitedPortsIds", qualifiedByName = "portsToIds")
    @Mapping(source = "sailboat.id", target = "sailboatId")
    @Mapping(source = "skipper.id", target = "skipperId")
    CruiseResponse toCruiseDto(Cruise cruise);

    @Mapping(source = "participantsIds", target = "participants")
    @Mapping(source = "visitedPortsIds", target = "visitedPorts")
    @Mapping(source = "sailboatId", target = "sailboat")
    @Mapping(source = "skipperId", target = "skipper")
    Cruise createCruiseFromDto(CreateCruiseRequest createCruiseRequest);


    @Named("sailorsToIds")
    default Set<UUID> sailorsToIds(Set<Sailor> sailors) {
        if (sailors == null) {
            return Set.of();
        }
        return sailors.stream()
                      .map(Sailor::getId)
                      .collect(Collectors.toSet());
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
}
