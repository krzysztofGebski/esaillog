package com.esaillog.sailor;

import com.esaillog.cruise.Cruise;
import com.esaillog.sailor.dtos.CreateSailorRequest;
import com.esaillog.sailor.dtos.SailorResponse;
import com.esaillog.sailor.dtos.UpdateSailorRequest;
import org.mapstruct.*;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SailorMapper {

    @Mapping(source = "cruises", target = "cruisesIds")
    @Mapping(source = "skipperedCruises", target = "skipperedCruisesIds")
    SailorResponse toSailorDto(Sailor sailor);

    Sailor createSailorFromDto(CreateSailorRequest createSailorRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSailorFromDto(UpdateSailorRequest updateSailorRequest, @MappingTarget Sailor sailor);

    default Set<UUID> cruisesToIds(Set<Cruise> cruises) {
        if (cruises == null) {
            return Set.of();
        }
        return cruises.stream()
                      .map(Cruise::getId)
                      .collect(Collectors.toSet());
    }
}