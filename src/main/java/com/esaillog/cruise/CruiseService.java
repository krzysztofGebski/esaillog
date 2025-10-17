package com.esaillog.cruise;

import com.esaillog.common.ReferenceMapper;
import com.esaillog.cruise.dtos.CreateCruiseRequest;
import com.esaillog.cruise.dtos.CruiseResponse;
import com.esaillog.cruise.dtos.UpdateCruiseRequest;
import com.esaillog.error.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CruiseService {
    private final CruiseRepository cruiseRepository;
    private final CruiseMapper cruiseMapper;
    private final ReferenceMapper referenceMapper;

    /**
     * Retrieves all cruises.
     *
     * @return a list of all cruises.
     */
    public List<CruiseResponse> findAll() {
        return cruiseRepository.findAll()
                               .stream()
                               .map(cruiseMapper::toCruiseDto)
                               .toList();
    }

    /**
     * Retrieves a cruise by its ID.
     *
     * @param id the ID of the cruise to retrieve.
     * @return the cruise with the given ID.
     * @throws ResourceNotFoundException if the cruise is not found.
     */
    public CruiseResponse findById(UUID id) {
        Cruise cruise = cruiseRepository.findById(id)
                                        .orElseThrow(() -> new ResourceNotFoundException("Cruise", "id", id));
        return cruiseMapper.toCruiseDto(cruise);
    }

    /**
     * Saves a new cruise.
     *
     * @param createCruiseRequest the request object containing the cruise data.
     * @return the saved cruise.
     */
    @Transactional
    public CruiseResponse save(CreateCruiseRequest createCruiseRequest) {
        Cruise cruise = cruiseMapper.createCruiseFromDto(createCruiseRequest);
        Cruise savedCruise = cruiseRepository.save(cruise);
        return cruiseMapper.toCruiseDto(savedCruise);
    }

    /**
     * Updates an existing cruise.
     *
     * @param id                  the ID of the cruise to update.
     * @param updateCruiseRequest the request object containing the updated cruise data.
     * @return the updated cruise.
     * @throws ResourceNotFoundException if the cruise is not found.
     */
    @Transactional
    public CruiseResponse update(UUID id, UpdateCruiseRequest updateCruiseRequest) {
        Cruise cruiseToUpdate = cruiseRepository.findById(id)
                                                .orElseThrow(() -> new ResourceNotFoundException("Cruise", "id", id));

        cruiseToUpdate.update(updateCruiseRequest, referenceMapper);

        return cruiseMapper.toCruiseDto(cruiseToUpdate);
    }

    /**
     * Deletes a cruise by its ID.
     *
     * @param id the ID of the cruise to delete.
     */
    @Transactional
    public void delete(UUID id) {
        cruiseRepository.deleteById(id);
    }
}
