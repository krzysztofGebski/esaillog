package com.esaillog.sailor;

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
import com.esaillog.sailor.dtos.CreateSailorRequest;
import com.esaillog.sailor.dtos.SailorResponse;
import com.esaillog.sailor.dtos.UpdateSailorRequest;

@Mapper(componentModel = "spring")
public interface SailorMapper {

        @Mapping(source = "cruises", target = "cruisesIds", qualifiedByName = "cruisesToIds")
        @Mapping(source = "skipperedCruises", target = "skipperedCruisesIds", qualifiedByName = "cruisesToIds")
        SailorResponse toSailorDto(Sailor sailor);

        @Mapping(target = "id", ignore = true)
        @Mapping(target = "cruises", ignore = true)
        @Mapping(target = "skipperedCruises", ignore = true)
        @Mapping(target = "createdAt", ignore = true)
        @Mapping(target = "updatedAt", ignore = true)
        Sailor createSailorFromDto(CreateSailorRequest createSailorRequest);

        @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
        Sailor updateSailorFromDto(UpdateSailorRequest updateSailorRequest, @MappingTarget Sailor sailor);

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