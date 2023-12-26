package com.esaillog.sailor;

import com.esaillog.training.Training;
import com.esaillog.training.TrainingNotFoundException;
import com.esaillog.training.TrainingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SailorService {

    private final SailorRepository sailorRepository;
    private final TrainingRepository trainingRepository;

    public List<SailorDTO> getSailors() {
        return sailorRepository.findAll().stream().map(SailorMapper::toDto).toList();
    }

    public SailorDTO getSailor(String uuid) throws SailorNotFoundException {
        return sailorRepository.findById(UUID.fromString(uuid)).map(SailorMapper::toDto).orElseThrow(SailorNotFoundException::new);
    }

    public void addSailor(SailorDTO sailorDTO) {
        sailorRepository.save(SailorMapper.toSailor(sailorDTO, fetchTrainingsBySailorDTO(sailorDTO)));
    }

    private List<Training> fetchTrainingsBySailorDTO(SailorDTO sailorDTO) {
        return sailorDTO.trainings().stream()
                        .map(this::findTrainingById)
                        .filter(Objects::nonNull)
                        .toList();
    }

    private Training findTrainingById(String trainingId) {
        try {
            return trainingRepository.findById(UUID.fromString(trainingId)).orElseThrow(TrainingNotFoundException::new);
        } catch (TrainingNotFoundException e) {
            log.warn(e.getMessage());
            return null;
        }
    }

    public void updateSailor(String uuid, SailorDTO sailorDTO) throws SailorNotFoundException {
        Sailor sailor = sailorRepository.findById(UUID.fromString(uuid)).orElseThrow(SailorNotFoundException::new);
        sailor.setFirstName(sailorDTO.firstName());
        sailor.setLastName(sailorDTO.lastName());
        sailor.setEmail(sailorDTO.email());
        sailor.setTrainings(fetchTrainingsBySailorDTO(sailorDTO));
    }

    public void deleteSailor(String uuid) throws SailorNotFoundException {
        if (sailorRepository.existsById(UUID.fromString(uuid))) {
            sailorRepository.deleteById(UUID.fromString(uuid));
        } else {
            throw new SailorNotFoundException();
        }
    }
}
