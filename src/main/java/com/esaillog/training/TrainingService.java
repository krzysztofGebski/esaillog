package com.esaillog.training;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TrainingService {
    private final TrainingRepository trainingRepository;

    public List<Training> getTrainings() {
        return trainingRepository.findAll();
    }

    public Training getTraining(UUID uuid) {
        return trainingRepository.findById(uuid).orElseThrow();
    }

    public void addTraining(Training training) {
        trainingRepository.save(training);
    }

    public void deleteTraining(UUID uuid) {
        trainingRepository.deleteById(uuid);
    }

    public void updateTraining(UUID uuid, Training training) {
        Training trainingToUpdate = getTraining(uuid);
        trainingToUpdate.setType(training.getType());
        trainingToUpdate.setHasCertificate(training.isHasCertificate());
        trainingToUpdate.setStartDate(training.getStartDate());
        trainingToUpdate.setEndDate(training.getEndDate());
    }

}
