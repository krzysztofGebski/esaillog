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

/**
 * Mapper for the Cruise entity.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = ReferenceMapper.class)
public interface CruiseMapper {

    /**
     * Maps a Cruise entity to a CruiseResponse DTO.
     *
     * @param cruise The Cruise entity to map.
     * @return The mapped CruiseResponse DTO.
     */
    @Mapping(source = "participants", target = "participantIds", qualifiedByName = "sailorsToIds")
    @Mapping(source = "visitedPorts", target = "visitedPortIds", qualifiedByName = "portsToIds")
    @Mapping(source = "sailboat.id", target = "sailboatId")
    @Mapping(source = "skipper.id", target = "skipperId")
    CruiseResponse toCruiseDto(Cruise cruise);

    /**
     * Maps a CreateCruiseRequest DTO to a Cruise entity.
     *
     * @param createCruiseRequest The CreateCruiseRequest DTO to map.
     * @return The mapped Cruise entity.
     */
    @Mapping(source = "participantIds", target = "participants")
    @Mapping(source = "visitedPortIds", target = "visitedPorts")
    @Mapping(source = "sailboatId", target = "sailboat")
    @Mapping(source = "skipperId", target = "skipper")
    Cruise createCruiseFromDto(CreateCruiseRequest createCruiseRequest);


    /**
     * Converts a set of Sailor objects to a set of their UUIDs.
     *
     * @param sailors The set of Sailor objects.
     * @return A set of UUIDs.
     */
    @Named("sailorsToIds")
    default Set<UUID> sailorsToIds(Set<Sailor> sailors) {
        if (sailors == null) {
            return Set.of();
        }
        return sailors.stream()
                      .map(Sailor::getId)
                      .collect(Collectors.toSet());
    }

    /**
     * Converts a set of Port objects to a set of their UUIDs.
     *
     * @param ports The set of Port objects.
     * @return A set of UUIDs.
     */
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
