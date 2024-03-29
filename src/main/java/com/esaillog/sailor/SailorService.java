package com.esaillog.sailor;

import com.esaillog.exam.Exam;
import com.esaillog.exam.ExamNotFoundException;
import com.esaillog.exam.ExamRepository;
import com.esaillog.training.Training;
import com.esaillog.training.TrainingNotFoundException;
import com.esaillog.training.TrainingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SailorService {

    private final SailorRepository sailorRepository;
    private final TrainingRepository trainingRepository;
    private final ExamRepository examRepository;

    @Transactional
    public List<SailorDTO> getSailors() {
        return sailorRepository.findAll().stream().map(SailorMapper::toDto).toList();
    }

    @Transactional
    public SailorDTO getSailor(String uuid) {
        UUID id = UUID.fromString(uuid);
        return sailorRepository.findById(id).map(SailorMapper::toDto).orElseThrow(() -> new SailorNotFoundException(uuid));
    }

    @Transactional
    public void addSailor(SailorDTO sailorDTO) {
        sailorRepository.save(SailorMapper.toSailor(sailorDTO, fetchTrainingsBySailorDTO(sailorDTO),
                fetchExamsBySailorDTO(sailorDTO)));
    }



    private Set<Training> fetchTrainingsBySailorDTO(SailorDTO sailorDTO) {
        return sailorDTO.trainings().stream().map(this::findTrainingById).collect(Collectors.toSet());
    }

    private Training findTrainingById(String trainingId) {
        UUID id = UUID.fromString(trainingId);
        return trainingRepository.findById(id).orElseThrow(() -> new TrainingNotFoundException(trainingId));
    }

    private Set<Exam> fetchExamsBySailorDTO(SailorDTO sailorDTO) {
        return sailorDTO.exams().stream().map(this::findExamById).collect(Collectors.toSet());
    }

    private Exam findExamById(String examId) {
        UUID id = UUID.fromString(examId);
        return examRepository.findById(id).orElseThrow(() -> new ExamNotFoundException(examId));
    }

    @Transactional
    public void updateSailor(String uuid, SailorDTO sailorDTO) {
        UUID id = UUID.fromString(uuid);
        Sailor sailor = sailorRepository.findById(id).orElseThrow(() -> new SailorNotFoundException(uuid));
        sailor.setFirstName(sailorDTO.firstName());
        sailor.setLastName(sailorDTO.lastName());
        sailor.setEmail(sailorDTO.email());
        sailor.setTrainings(fetchTrainingsBySailorDTO(sailorDTO));
    }

    public void deleteSailor(String uuid) {
        UUID id = UUID.fromString(uuid);
        if (sailorRepository.existsById(id)) {
            sailorRepository.deleteById(id);
        } else {
            throw new SailorNotFoundException(uuid);
        }
    }
}
