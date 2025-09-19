package com.esaillog.sailboat;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.esaillog.cruise.Cruise;
import com.esaillog.port.Port;
import com.esaillog.sailboat.dtos.CreateSailboatRequest;
import com.esaillog.sailboat.dtos.SailboatResponse;
import com.esaillog.sailboat.dtos.UpdateSailboatRequest;

@Mapper(componentModel = "spring")
public interface SailboatMapper {

    @Mapping(source = "homePort", target = "homePortId", qualifiedByName = "portToId")
    @Mapping(source = "cruises", target = "cruiseIds", qualifiedByName = "cruisesToIds")
    SailboatResponse toSailboatDto(Sailboat sailboat);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "homePort", ignore = true)
    @Mapping(target = "cruises", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Sailboat createSailboatFromDto(CreateSailboatRequest createSailboatRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
    Sailboat updateSailboatFromDto(UpdateSailboatRequest updateSailboatRequest, @MappingTarget Sailboat sailboat);

    @Named("portToId")
    default UUID portToId(Port port) {
        if (port == null) {
            return null;
        }
        return port.getId();
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