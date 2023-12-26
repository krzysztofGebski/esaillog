package com.esaillog.sailor;

import com.esaillog.training.Training;
import com.esaillog.training.TrainingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SailorService {

    private final SailorRepository sailorRepository;
    private final TrainingRepository trainingRepository;

    public List<SailorDTO> getSailors() {
        return sailorRepository.findAll().stream().map(SailorMapper::toDto).toList();
    }

    public SailorDTO getSailor(String uuid) {
        return sailorRepository.findById(UUID.fromString(uuid)).map(SailorMapper::toDto).orElseThrow();
    }

    public void addSailor(SailorDTO sailorDTO) {
        sailorRepository.save(SailorMapper.toSailor(sailorDTO, fetchTrainingsBySailorDTO(sailorDTO)));
    }

    private List<Training> fetchTrainingsBySailorDTO(SailorDTO sailorDTO) {
        return sailorDTO.trainings().stream().map(trainingId -> trainingRepository.findById(UUID.fromString(trainingId)).orElseThrow()).toList();
    }

    public void updateSailor(String uuid, SailorDTO sailorDTO) {
        Sailor sailor = sailorRepository.findById(UUID.fromString(uuid)).orElseThrow();
        sailor.setFirstName(sailorDTO.firstName());
        sailor.setLastName(sailorDTO.lastName());
        sailor.setEmail(sailorDTO.email());
        sailor.setTrainings(fetchTrainingsBySailorDTO(sailorDTO));
    }

    public void deleteSailor(String uuid) {
        sailorRepository.deleteById(UUID.fromString(uuid));
    }
}
