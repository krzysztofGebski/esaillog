package com.esaillog.port;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.esaillog.cruise.Cruise;
import com.esaillog.sailboat.Sailboat;

@Mapper(componentModel = "spring")
public interface PortMapper {
 
    @Mapping( source = "sailboats", target = "sailboatsIds", qualifiedByName = "sailboatsToIds")
    @Mapping( source = "cruises", target = "cruisesIds", qualifiedByName = "cruisesToIds")
    PortDto toPortDto(Port port);

    @Mapping(target = "sailboats", ignore = true)
    @Mapping(target = "cruises", ignore = true)
    @Mapping(target = "id", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Port toPort(PortDto portDto);

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