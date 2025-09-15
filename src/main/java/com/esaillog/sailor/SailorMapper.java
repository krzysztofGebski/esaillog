package com.esaillog.sailor;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.esaillog.cruise.Cruise;
import java.util.Collections;

@Mapper(componentModel = "spring")
public interface SailorMapper {

        @Mapping(source = "cruises", target = "cruisesIds", qualifiedByName = "cruisesToIds")
        @Mapping(source = "skipperedCruises", target = "skipperedCruisesIds", qualifiedByName = "cruisesToIds")
        SailorDto toSailorDto(Sailor sailor);

        @Mapping(target = "cruises", ignore = true)
        @Mapping(target = "skipperedCruises", ignore = true)
        Sailor toSailor(SailorDto sailorDto);

        @Named("cruisesToIds")
        default Set<UUID> cruiseToId(Set<Cruise> cruises) {
                if (cruises == null) {
                        return Collections.emptySet();
                }
                return cruises.stream()
                                .map(Cruise::getId)
                                .collect(Collectors.toSet());
        }
}