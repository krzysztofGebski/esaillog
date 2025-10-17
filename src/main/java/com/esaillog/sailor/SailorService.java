package com.esaillog.sailor;

import com.esaillog.error.ResourceNotFoundException;
import com.esaillog.sailor.dtos.CreateSailorRequest;
import com.esaillog.sailor.dtos.SailorResponse;
import com.esaillog.sailor.dtos.UpdateSailorRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Service class for managing sailors.
 * This class contains the business logic for sailor-related operations and
 * acts as an intermediary between the controller and the repository.
 */
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class SailorService {
    private final SailorRepository sailorRepository;
    private final SailorMapper sailorMapper;

    /**
     * Retrieves all sailors from the database.
     *
     * @return a list of {@link SailorResponse} DTOs.
     */
    public List<SailorResponse> findAll() {
        return sailorRepository.findAll().stream()
                .map(sailorMapper::toSailorDto)
                .toList();
    }

    /**
     * Finds a single sailor by their ID.
     *
     * @param id the UUID of the sailor.
     * @return a {@link SailorResponse} DTO for the found sailor.
     * @throws ResourceNotFoundException if no sailor with the given ID is found.
     */
    public SailorResponse findById(UUID id) {
        Sailor sailor = sailorRepository.findById(id)
                                      .orElseThrow(() -> new ResourceNotFoundException("Sailor", "id", id));
        return sailorMapper.toSailorDto(sailor);
    }

    /**
     * Creates and saves a new sailor based on the provided request data.
     *
     * @param createSailorRequest DTO containing the data for the new sailor.
     * @return a {@link SailorResponse} DTO for the newly created sailor.
     */
    @Transactional
    public SailorResponse save(CreateSailorRequest createSailorRequest) {
        Sailor sailor = sailorMapper.createSailorFromDto(createSailorRequest);
        Sailor savedSailor = sailorRepository.save(sailor);
        return sailorMapper.toSailorDto(savedSailor);
    }

    /**
     * Updates an existing sailor with new data.
     *
     * @param id                  the UUID of the sailor to update.
     * @param updateSailorRequest DTO containing the new data.
     * @return a {@link SailorResponse} DTO for the updated sailor.
     * @throws ResourceNotFoundException if no sailor with the given ID is found.
     */
    @Transactional
    public SailorResponse update(UUID id, UpdateSailorRequest updateSailorRequest) {
        Sailor sailorToUpdate = sailorRepository.findById(id)
                                                .orElseThrow(() -> new ResourceNotFoundException("Sailor", "id", id));

        sailorToUpdate.update(updateSailorRequest);

        return sailorMapper.toSailorDto(sailorToUpdate);
    }

    /**
     * Deletes a sailor by their ID.
     *
     * @param id the UUID of the sailor to delete.
     */
    @Transactional
    public void delete(UUID id) {
        sailorRepository.deleteById(id);
    }
}
