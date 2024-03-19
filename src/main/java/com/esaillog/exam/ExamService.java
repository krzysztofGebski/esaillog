package com.esaillog.exam;

import com.esaillog.sailor.Sailor;
import com.esaillog.sailor.SailorNotFoundException;
import com.esaillog.sailor.SailorRepository;
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
public class ExamService {
    private final ExamRepository examRepository;
    private final SailorRepository sailorRepository;

    @Transactional
    public List<ExamDTO> getExams() {
        return examRepository.findAll().stream().map(ExamMapper::toDTO).toList();
    }

    @Transactional
    public ExamDTO getExam(String uuid) {
        UUID id = UUID.fromString(uuid);
        return examRepository.findById(id).map(ExamMapper::toDTO).orElseThrow(() -> new ExamNotFoundException(uuid));
    }

    @Transactional
    public void addExam(ExamDTO examDTO) {
        examRepository.save(ExamMapper.toExam(examDTO, fetchSailorsByExamDTO(examDTO)));
    }

    private Set<Sailor> fetchSailorsByExamDTO(ExamDTO examDTO) {
        return examDTO.participants().stream().map(this::findSailorById).collect(Collectors.toSet());
    }

    private Sailor findSailorById(String sailorId) {
        UUID id = UUID.fromString(sailorId);
        return sailorRepository.findById(id).orElseThrow(() -> new SailorNotFoundException(sailorId));
    }

    @Transactional
    public void updateExam(String id, ExamDTO examDTO) {
        Exam existingExam = examRepository.findById(UUID.fromString(id)).orElseThrow(() -> new ExamNotFoundException(id));
        Exam updatedExam = ExamMapper.toExam(examDTO, fetchSailorsByExamDTO(examDTO));
        updatedExam.setId(existingExam.getId());
        examRepository.save(updatedExam);
    }

}
