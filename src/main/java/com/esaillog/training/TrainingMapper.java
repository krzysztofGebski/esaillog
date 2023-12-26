package com.esaillog.training;

import com.esaillog.sailor.SailorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrainingMapper {

    private final TrainingService trainingService;
    private final SailorService sailorService;

    public List<TrainingDTO> getTrainings() {
        return trainingService.getTrainings().stream().map(this::toTrainingDTO).toList();
    }

    private TrainingDTO toTrainingDTO(Training training) {
        return new TrainingDTO(training.getId().toString(),
                               training.getName(),
                               training.getType(),
                               training.isHasCertificate(),
                               training.getStartDate().toString(),
                               training.getEndDate().toString(),
                               training.getSailors().stream().map(sailor -> sailor.getId().toString()).toList());
    }

    public void deleteTraining(String id) {
        trainingService.deleteTraining(UUID.fromString(id));
    }

    public void updateTraining(String uuid, TrainingDTO trainingDTO) {
        trainingService.updateTraining(UUID.fromString(uuid), toTraining(trainingDTO));
    }

    private Training toTraining(TrainingDTO trainingDTO) {
        Training training = new Training();
        training.setId(UUID.fromString(trainingDTO.id()));
        training.setName(trainingDTO.name());
        training.setType(trainingDTO.type());
        training.setHasCertificate(trainingDTO.hasCertificate());
        training.setStartDate(LocalDate.parse(trainingDTO.startDate()));
        training.setEndDate(LocalDate.parse(trainingDTO.endDate()));
        training.setSailors(trainingDTO.sailors().stream().map(sailorId -> sailorService.getSailor(UUID.fromString(sailorId))).toList());
        return training;
    }

    public TrainingDTO getTraining(String id) {
        return toTrainingDTO(trainingService.getTraining(UUID.fromString(id)));
    }

    public void addTraining(TrainingDTO trainingDTO) {
        trainingService.addTraining(toTraining(trainingDTO));
    }


}
