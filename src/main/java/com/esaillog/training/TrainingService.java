package com.esaillog.training;

import com.esaillog.sailor.Sailor;
import com.esaillog.sailor.SailorNotFoundException;
import com.esaillog.sailor.SailorRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class TrainingService {
    private final TrainingRepository trainingRepository;
    private final SailorRepository sailorRepository;

    @Transactional
    public List<TrainingDTO> getTrainings() {
        return trainingRepository.findAll().stream().map(TrainingMapper::toDto).toList();
    }

    @Transactional
    public TrainingDTO getTraining(String uuid) {
        UUID id = UUID.fromString(uuid);
        return trainingRepository.findById(id).map(TrainingMapper::toDto).orElseThrow(() -> new TrainingNotFoundException(uuid));
    }

    @Transactional
    public void addTraining(TrainingDTO trainingDTO) {
        trainingRepository.save(TrainingMapper.toTraining(trainingDTO, fetchSailorsByTrainingDTO(trainingDTO)));
    }

    private Set<Sailor> fetchSailorsByTrainingDTO(TrainingDTO trainingDTO) {
        return trainingDTO.sailors().stream().map(this::findSailorById).collect(Collectors.toSet());
    }

    private Sailor findSailorById(String sailorId) {
        UUID id = UUID.fromString(sailorId);
        return sailorRepository.findById(id).orElseThrow(() -> new SailorNotFoundException(sailorId));
    }

    @Transactional
    public void updateTraining(String uuid, TrainingDTO trainingDTO) {
        UUID id = UUID.fromString(uuid);
        Training training = trainingRepository.findById(id).orElseThrow(() -> new TrainingNotFoundException(uuid));
        training.setName(trainingDTO.name());
        training.setType(trainingDTO.type());
        training.setHasCertificate(trainingDTO.hasCertificate());
        training.setStartDate(LocalDate.parse(trainingDTO.startDate()));
        training.setEndDate(LocalDate.parse(trainingDTO.endDate()));
        training.setSailors(fetchSailorsByTrainingDTO(trainingDTO));
    }

    public void deleteTraining(String uuid) {
        UUID id = UUID.fromString(uuid);
        if (trainingRepository.existsById(id)) {
            trainingRepository.deleteById(id);
        } else {
            throw new TrainingNotFoundException(uuid);
        }
    }
}
