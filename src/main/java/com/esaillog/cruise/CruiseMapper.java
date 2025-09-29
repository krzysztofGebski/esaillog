package com.esaillog.cruise;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.esaillog.common.ReferenceMapper;
import com.esaillog.cruise.dtos.CreateCruiseRequest;
import com.esaillog.cruise.dtos.UpdateCruiseRequest;
import org.mapstruct.*;

import com.esaillog.cruise.dtos.CruiseResponse;
import com.esaillog.port.Port;
import com.esaillog.sailor.Sailor;

@Mapper(componentModel = "spring", uses = {ReferenceMapper.class})
public interface CruiseMapper {

    @Mapping(source = "participants", target = "participantsIds")
    @Mapping(source = "visitedPorts", target = "visitedPortsIds")
    @Mapping(source = "sailboat.id", target = "sailboatId")
    @Mapping(source = "skipper.id", target = "skipperId")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    CruiseResponse toCruiseDto(Cruise cruise);

    @Mapping(target = "id", ignore = true)
    @Mapping(source= "participantsIds", target = "participants")
    @Mapping(source= "visitedPortsIds", target = "visitedPorts")
    @Mapping(source = "sailboatId", target = "sailboat")
    @Mapping(source = "skipperId", target = "skipper")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Cruise createCruiseFromDto(CreateCruiseRequest createCruiseRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy =
            ReportingPolicy.IGNORE)
    void updateCruiseFromDto(UpdateCruiseRequest updateCruiseRequest, @MappingTarget Cruise cruise);


    default Set<UUID> sailorsToIds(Set<Sailor> sailors) {
        if (sailors == null) {
            return Set.of();
        }
        return sailors.stream().map(Sailor::getId).collect(Collectors.toSet());
    }

    default Set<UUID> portsToIds(Set<Port> ports) {
        if (ports == null) {
            return Set.of();
        }
        return ports.stream().map(Port::getId).collect(Collectors.toSet());
    }
}
