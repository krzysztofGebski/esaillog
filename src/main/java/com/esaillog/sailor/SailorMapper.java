package com.esaillog.sailor;

import com.esaillog.cruise.Cruise;
import com.esaillog.sailor.dtos.CreateSailorRequest;
import com.esaillog.sailor.dtos.SailorResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SailorMapper {

    @Mapping(source = "cruises", target = "cruiseIds")
    @Mapping(source = "skipperedCruises", target = "skipperedCruiseIds")
    SailorResponse toSailorDto(Sailor sailor);

    Sailor createSailorFromDto(CreateSailorRequest createSailorRequest);

    default Set<UUID> cruisesToIds(Set<Cruise> cruises) {
        if (cruises == null) {
            return Set.of();
        }
        return cruises.stream()
                      .map(Cruise::getId)
                      .collect(Collectors.toSet());
    }
}