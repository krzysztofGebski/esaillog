package com.esaillog.port;

import com.esaillog.cruise.Cruise;
import com.esaillog.port.dtos.CreatePortRequest;
import com.esaillog.port.dtos.PortResponse;
import com.esaillog.sailboat.Sailboat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PortMapper {

    @Mapping(source = "sailboats", target = "sailboatsIds", qualifiedByName = "sailboatsToIds")
    @Mapping(source = "cruises", target = "cruiseIds", qualifiedByName = "cruisesToIds")
    PortResponse toPortDto(Port port);

    Port createPortFromDto(CreatePortRequest createPortRequest);

    @Named("sailboatsToIds")
    default Set<UUID> sailboatsToIds(Set<Sailboat> sailboats) {
        if (sailboats == null) {
            return Set.of();
        }
        return sailboats.stream()
                        .map(Sailboat::getId)
                        .collect(Collectors.toSet());
    }

    @Named("cruisesToIds")
    default Set<UUID> cruisesToIds(Set<Cruise> cruises) {
        if (cruises == null) {
            return Set.of();
        }
        return cruises.stream()
                      .map(Cruise::getId)
                      .collect(Collectors.toSet());
    }

}