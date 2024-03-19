package com.esaillog.sailor;

import com.esaillog.exam.Exam;
import com.esaillog.training.Training;

import java.util.Set;
import java.util.UUID;

interface SailorMapper {

    static Sailor toSailor(SailorDTO sailorDTO, Set<Training> trainings, Set<Exam> exams) {
        Sailor sailor = new Sailor();
        sailor.setId(sailorDTO.id() != null ? UUID.fromString(sailorDTO.id()) : UUID.randomUUID());
        sailor.setFirstName(sailorDTO.firstName());
        sailor.setLastName(sailorDTO.lastName());
        sailor.setEmail(sailorDTO.email());
        sailor.setTrainings(trainings);
        sailor.setExams(exams);
        return sailor;
    }

    static SailorDTO toDto(Sailor sailor) {
        return new SailorDTO(sailor.getId().toString(),
                sailor.getFirstName(),
                sailor.getLastName(),
                sailor.getEmail(),
                sailor.getTrainings().stream().map(Training::getId).map(UUID::toString).toList(),
                sailor.getExams().stream().map(Exam::getId).map(UUID::toString).toList()

        );
    }
}
