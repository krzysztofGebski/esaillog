package com.esaillog.cruise;

import com.esaillog.common.ReferenceMapper;
import com.esaillog.cruise.dtos.CreateCruiseRequest;
import com.esaillog.cruise.dtos.CruiseResponse;
import com.esaillog.cruise.dtos.UpdateCruiseRequest;
import com.esaillog.error.EntityNotFoundException;
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

    public List<CruiseResponse> findAll() {
        return cruiseRepository.findAll()
                               .stream()
                               .map(cruiseMapper::toCruiseDto)
                               .toList();
    }

    public CruiseResponse findById(UUID id) {
        Cruise cruise = cruiseRepository.findById(id)
                                        .orElseThrow(() -> new EntityNotFoundException("Cruise with id " + id + " not found"));
        return cruiseMapper.toCruiseDto(cruise);
    }

    @Transactional
    public CruiseResponse save(CreateCruiseRequest createCruiseRequest) {
        Cruise cruise = cruiseMapper.createCruiseFromDto(createCruiseRequest);
        Cruise savedCruise = cruiseRepository.save(cruise);
        return cruiseMapper.toCruiseDto(savedCruise);
    }

    @Transactional
    public CruiseResponse update(UUID id, UpdateCruiseRequest updateCruiseRequest) {
        Cruise cruiseToUpdate = cruiseRepository.findById(id)
                                                .orElseThrow(() -> new EntityNotFoundException("Cruise with id " + id + " not found"));

        cruiseToUpdate.update(updateCruiseRequest, referenceMapper);

        return cruiseMapper.toCruiseDto(cruiseToUpdate);
    }

    @Transactional
    public void delete(UUID id) {
        cruiseRepository.deleteById(id);
    }
}
