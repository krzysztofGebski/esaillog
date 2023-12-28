package com.esaillog.training;

import com.esaillog.sailor.Sailor;
import com.esaillog.sailor.SailorNotFoundException;
import com.esaillog.sailor.SailorRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class TrainingService {
    private final TrainingRepository trainingRepository;
    private final SailorRepository sailorRepository;

    public List<TrainingDTO> getTrainings() {
        return trainingRepository.findAll().stream().map(TrainingMapper::toDto).toList();
    }

    public TrainingDTO getTraining(String uuid) throws TrainingNotFoundException {
        UUID id = UUID.fromString(uuid);
        return trainingRepository.findById(id).map(TrainingMapper::toDto).orElseThrow(() -> new TrainingNotFoundException(uuid));
    }

    public void addTraining(TrainingDTO trainingDTO) {
        trainingRepository.save(TrainingMapper.toTraining(trainingDTO, fetchSailorsByTrainingDTO(trainingDTO)));
    }

    private List<Sailor> fetchSailorsByTrainingDTO(TrainingDTO trainingDTO) {
        return trainingDTO.sailors().stream().map(this::findSailorById).filter(Objects::nonNull).toList();
    }

    private Sailor findSailorById(String sailorId) {
        try {
            UUID id = UUID.fromString(sailorId);
            return sailorRepository.findById(id).orElseThrow(() -> new SailorNotFoundException(sailorId));
        } catch (SailorNotFoundException e) {
            log.warn(e.getMessage());
            return null;
        }
    }

    public void updateTraining(String uuid, TrainingDTO trainingDTO) throws TrainingNotFoundException {
        UUID id = UUID.fromString(uuid);
        Training training = trainingRepository.findById(id).orElseThrow(() -> new TrainingNotFoundException(uuid));
        training.setName(trainingDTO.name());
        training.setType(trainingDTO.type());
        training.setHasCertificate(trainingDTO.hasCertificate());
        training.setStartDate(LocalDate.parse(trainingDTO.startDate()));
        training.setEndDate(LocalDate.parse(trainingDTO.endDate()));
        training.setSailors(fetchSailorsByTrainingDTO(trainingDTO));
    }

    public void deleteTraining(String uuid) throws TrainingNotFoundException {
        UUID id = UUID.fromString(uuid);
        if (trainingRepository.existsById(id)) {
            trainingRepository.deleteById(id);
        } else {
            throw new TrainingNotFoundException(uuid);
        }
    }
}
