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
        UUID id = UUID.fromString(uuid);
        return sailorRepository.findById(id).map(SailorMapper::toDto).orElseThrow(() -> new SailorNotFoundException(uuid));
    }

    public void addSailor(SailorDTO sailorDTO) {
        sailorRepository.save(SailorMapper.toSailor(sailorDTO, fetchTrainingsBySailorDTO(sailorDTO)));
    }

    private List<Training> fetchTrainingsBySailorDTO(SailorDTO sailorDTO) {
        return sailorDTO.trainings().stream().map(this::findTrainingById).filter(Objects::nonNull).toList();
    }

    private Training findTrainingById(String trainingId) {
        try {
            UUID id = UUID.fromString(trainingId);
            return trainingRepository.findById(id).orElseThrow(() -> new TrainingNotFoundException(trainingId));
        } catch (TrainingNotFoundException e) {
            log.warn(e.getMessage());
            return null;
        }
    }

    public void updateSailor(String uuid, SailorDTO sailorDTO) throws SailorNotFoundException {
        UUID id = UUID.fromString(uuid);
        Sailor sailor = sailorRepository.findById(id).orElseThrow(() -> new SailorNotFoundException(uuid));
        sailor.setFirstName(sailorDTO.firstName());
        sailor.setLastName(sailorDTO.lastName());
        sailor.setEmail(sailorDTO.email());
        sailor.setTrainings(fetchTrainingsBySailorDTO(sailorDTO));
    }

    public void deleteSailor(String uuid) throws SailorNotFoundException {
        UUID id = UUID.fromString(uuid);
        if (sailorRepository.existsById(id)) {
            sailorRepository.deleteById(id);
        } else {
            throw new SailorNotFoundException(uuid);
        }
    }
}
