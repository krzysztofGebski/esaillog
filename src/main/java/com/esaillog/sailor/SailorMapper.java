package com.esaillog.sailor;

import com.esaillog.training.Training;

import java.util.List;
import java.util.UUID;
interface SailorMapper {

    static Sailor toSailor(SailorDTO sailorDTO, List<Training> trainings) {
        Sailor sailor = new Sailor();
        sailor.setId(UUID.fromString(sailorDTO.id()));
        sailor.setFirstName(sailorDTO.firstName());
        sailor.setLastName(sailorDTO.lastName());
        sailor.setEmail(sailorDTO.email());
        sailor.setTrainings(trainings);
        return sailor;
    }

    static SailorDTO toDto(Sailor sailor) {
        return new SailorDTO(sailor.getId().toString(),
                             sailor.getFirstName(),
                             sailor.getLastName(),
                             sailor.getEmail(),
                             sailor.getTrainings().stream().map(Training::getId).map(UUID::toString).toList()

        );
    }
}
