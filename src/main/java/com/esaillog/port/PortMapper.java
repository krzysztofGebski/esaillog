package com.esaillog.port;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.esaillog.cruise.Cruise;
import com.esaillog.port.dtos.CreatePortRequest;
import com.esaillog.port.dtos.PortResponse;
import com.esaillog.port.dtos.UpdatePortRequest;
import com.esaillog.sailboat.Sailboat;

@Mapper(componentModel = "spring")
public interface PortMapper {
 
    @Mapping( source = "sailboats", target = "sailboatsIds", qualifiedByName = "sailboatsToIds")
    @Mapping( source = "cruises", target = "cruisesIds", qualifiedByName = "cruisesToIds")
    @Mapping( source = "updatedAt", target = "updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    PortResponse toPortDto(Port port);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sailboats", ignore = true)
    @Mapping(target = "cruises", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Port createPortFromDto(CreatePortRequest createPortRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
    Port updatePortFromDto(UpdatePortRequest updatePortRequest, @MappingTarget Port port);

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