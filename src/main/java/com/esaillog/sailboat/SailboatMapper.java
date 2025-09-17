package com.esaillog.sailboat;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.esaillog.cruise.Cruise;
import com.esaillog.port.Port;
import com.esaillog.sailboat.dtos.SailboatResponse;

@Mapper(componentModel = "spring")
public interface SailboatMapper {
    
    @Mapping(source = "cruises", target = "cruiseIds", qualifiedByName = "cruisesToIds")
    @Mapping(source = "homePort", target = "homePortId", qualifiedByName = "portToId")
    SailboatResponse toSailboatDto(Sailboat sailboat);

    @Mapping(target = "homePort", ignore = true)
    @Mapping(target = "cruises", ignore = true)
    @Mapping(target = "id", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Sailboat toSailboat(SailboatResponse sailboatDto);

    @Named("cruisesToIds")
    default Set<UUID> cruisesToIds(Set<Cruise> cruises) {
        if (cruises == null) {
            return Set.of();
        }
        return cruises.stream()
                .map(Cruise::getId)
                .collect(Collectors.toSet());
    }

    @Named("portToId")
    default UUID portToId(Port port) {
        if (port == null) {
            return null;
        }
        return port.getId();
    }
   
}