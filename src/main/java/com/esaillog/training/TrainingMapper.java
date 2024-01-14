package com.esaillog.training;

import com.esaillog.sailor.Sailor;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

interface TrainingMapper {

    static Training toTraining(TrainingDTO trainingDTO, Set<Sailor> sailors) {
        Training training = new Training();
        training.setId(trainingDTO.id() != null ? UUID.fromString(trainingDTO.id()) : UUID.randomUUID());
        training.setName(trainingDTO.name());
        training.setType(trainingDTO.type());
        training.setHasCertificate(trainingDTO.hasCertificate());
        training.setStartDate(LocalDate.parse(trainingDTO.startDate()));
        training.setEndDate(LocalDate.parse(trainingDTO.endDate()));
        training.setSailors(sailors);
        return training;
    }

    static TrainingDTO toDto(Training training) {
        return new TrainingDTO(
                training.getId().toString(),
                training.getName(),
                training.getType(),
                training.isHasCertificate(),
                training.getStartDate().toString(),
                training.getEndDate().toString(),
                training.getSailors().stream().map(Sailor::getId).map(UUID::toString).toList()
        );
    }
}
