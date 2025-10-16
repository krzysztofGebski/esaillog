package com.esaillog.sailboat;

import com.esaillog.cruise.Cruise;
import com.esaillog.sailboat.dtos.CreateSailboatRequest;
import com.esaillog.sailboat.dtos.SailboatResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface SailboatMapper {

    @Mapping(source = "cruises", target = "cruiseIds")
    SailboatResponse toSailboatDto(Sailboat sailboat);

    Sailboat createSailboatFromDto(CreateSailboatRequest createSailboatRequest);

    default Set<UUID> cruisesToIds(Set<Cruise> cruises) {
        if (cruises == null) {
            return Set.of();
        }
        return cruises.stream()
                      .map(Cruise::getId)
                      .collect(Collectors.toSet());
    }

}