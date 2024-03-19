package com.esaillog.exam;

import com.esaillog.sailor.Sailor;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public interface ExamMapper {

    static Exam toExam(ExamDTO examDTO, Set<Sailor> sailors) {
        Exam exam = new Exam();
        exam.setId(examDTO.id() != null ? UUID.fromString(examDTO.id()) : UUID.randomUUID());
        exam.setName(examDTO.name());
        exam.setNumber(examDTO.number());
        exam.setDate(LocalDate.parse(examDTO.date()));
        exam.setType(examDTO.type());
        exam.setParticipants(sailors);
        return exam;
    }

    static ExamDTO toDTO(Exam exam) {
        return new ExamDTO(
                exam.getId().toString(),
                exam.getName(),
                exam.getNumber(),
                exam.getDate().toString(),
                exam.getType(),
                exam.getParticipants().stream().map(Sailor::getId).map(UUID::toString).toList()
        );
    }
}
