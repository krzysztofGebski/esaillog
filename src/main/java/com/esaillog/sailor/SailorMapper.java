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

    public SailorDTO getSailor(String uuid) {
        return toSailorDTO(sailorService.getSailor(UUID.fromString(uuid)));
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

    public void updateSailor(String uuid, SailorDTO sailorDTO) {
        sailorService.updateSailor(UUID.fromString(uuid), toSailor(sailorDTO));
    }

    public void deleteSailor(String uuid) {
        sailorService.deleteSailor(UUID.fromString(uuid));
    }
}
