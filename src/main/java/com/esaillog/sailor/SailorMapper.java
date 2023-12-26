package com.esaillog.sailor;

import com.esaillog.training.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SailorMapper {

    private final TrainingService trainingService;
    private final SailorService sailorService;

    public List<SailorDTO> getSailors() {
        return sailorService.getSailors().stream().map(this::toSailorDTO).toList();
    }

    private SailorDTO toSailorDTO(Sailor sailor) {
        return new SailorDTO(sailor.getId().toString(),
                             sailor.getFirstName(),
                             sailor.getLastName(),
                             sailor.getEmail(),
                             sailor.getTrainings().stream().map(training -> training.getId().toString()).toList());
    }

    public SailorDTO getSailor(UUID id) {
        return toSailorDTO(sailorService.getSailor(id));
    }

    public void addSailor(SailorDTO sailorDTO) {
        sailorService.addSailor(toSailor(sailorDTO));
    }

    private Sailor toSailor(SailorDTO sailorDTO) {
        Sailor sailor = new Sailor();
        sailor.setId((sailorDTO.id().isEmpty()) ? null : UUID.fromString(sailorDTO.id()));
        sailor.setFirstName(sailorDTO.firstName());
        sailor.setLastName(sailorDTO.lastName());
        sailor.setEmail(sailorDTO.email());
        sailor.setTrainings(sailorDTO.trainings().stream().map(trainingId -> trainingService.getTraining(UUID.fromString(trainingId))).toList());
        return sailor;
    }

    public void updateSailor(UUID id, SailorDTO sailorDTO) {
        sailorService.updateSailor(id, toSailor(sailorDTO));
    }

    public void deleteSailor(UUID id) {
        sailorService.deleteSailor(id);
    }
}
