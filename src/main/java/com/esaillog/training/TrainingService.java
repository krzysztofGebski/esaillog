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
        return trainingRepository.findById(UUID.fromString(uuid)).map(TrainingMapper::toDto).orElseThrow(TrainingNotFoundException::new);
    }

    public void addTraining(TrainingDTO trainingDTO) {
        trainingRepository.save(TrainingMapper.toTraining(trainingDTO, fetchSailorsByTrainingDTO(trainingDTO)));
    }

    private List<Sailor> fetchSailorsByTrainingDTO(TrainingDTO trainingDTO) {
        return trainingDTO.sailors().stream().map(sailorId -> {
            try {
                return sailorRepository.findById(UUID.fromString(sailorId)).orElseThrow(SailorNotFoundException::new);
            } catch (SailorNotFoundException e) {
                log.warn(e.getMessage());
                return null;
            }
        }).filter(Objects::nonNull).toList();
    }

    public void updateTraining(String uuid, TrainingDTO trainingDTO) throws TrainingNotFoundException {
        Training training = trainingRepository.findById(UUID.fromString(uuid)).orElseThrow(TrainingNotFoundException::new);
        training.setName(trainingDTO.name());
        training.setType(trainingDTO.type());
        training.setHasCertificate(trainingDTO.hasCertificate());
        training.setStartDate(LocalDate.parse(trainingDTO.startDate()));
        training.setEndDate(LocalDate.parse(trainingDTO.endDate()));
        training.setSailors(fetchSailorsByTrainingDTO(trainingDTO));
    }

    public void deleteTraining(String uuid) throws TrainingNotFoundException {
        if(trainingRepository.existsById(UUID.fromString(uuid))) {
            trainingRepository.deleteById(UUID.fromString(uuid));
        } else {
            throw new TrainingNotFoundException();
        }
    }
}
