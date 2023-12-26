package com.esaillog.training;

import com.esaillog.sailor.Sailor;
import com.esaillog.sailor.SailorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TrainingService {
    private final TrainingRepository trainingRepository;
    private final SailorRepository sailorRepository;

    public List<TrainingDTO> getTrainings() {
        return trainingRepository.findAll().stream().map(TrainingMapper::toDto).toList();
    }

    public TrainingDTO getTraining(String uuid) {
        return trainingRepository.findById(UUID.fromString(uuid)).map(TrainingMapper::toDto).orElseThrow();
    }

    public void addTraining(TrainingDTO trainingDTO) {
        trainingRepository.save(TrainingMapper.toTraining(trainingDTO, fetchSailorsByTrainingDTO(trainingDTO)));
    }

    private List<Sailor> fetchSailorsByTrainingDTO(TrainingDTO trainingDTO) {
        return trainingDTO.sailors().stream().map(sailorId -> sailorRepository.findById(UUID.fromString(sailorId)).orElseThrow()).toList();
    }

    public void updateTraining(String uuid, TrainingDTO trainingDTO) {
        Training training = trainingRepository.findById(UUID.fromString(uuid)).orElseThrow();
        training.setName(trainingDTO.name());
        training.setType(trainingDTO.type());
        training.setHasCertificate(trainingDTO.hasCertificate());
        training.setStartDate(LocalDate.parse(trainingDTO.startDate()));
        training.setEndDate(LocalDate.parse(trainingDTO.endDate()));
        training.setSailors(fetchSailorsByTrainingDTO(trainingDTO));
    }

    public void deleteTraining(String uuid) {
        trainingRepository.deleteById(UUID.fromString(uuid));
    }
}
