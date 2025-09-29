package com.esaillog.cruise;

import com.esaillog.cruise.dtos.CreateCruiseRequest;
import com.esaillog.cruise.dtos.CruiseResponse;
import com.esaillog.cruise.dtos.UpdateCruiseRequest;
import com.esaillog.error.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CruiseService {
    private final CruiseRepository cruiseRepository;
    private final CruiseMapper cruiseMapper;

    public List<CruiseResponse> findAll() {
        return cruiseRepository.findAll().stream().map(cruiseMapper::toCruiseDto).toList();
    }

    public CruiseResponse findById(UUID id) {
        Cruise cruise = cruiseRepository.findById(id)
                                        .orElseThrow(() -> new EntityNotFoundException("Cruise with id " + id + " not found"));
        return cruiseMapper.toCruiseDto(cruise);
    }

    public CruiseResponse save(CreateCruiseRequest createCruiseRequest) {
        Cruise cruise = cruiseMapper.createCruiseFromDto(createCruiseRequest);
        Cruise savedCruise = cruiseRepository.save(cruise);
        return cruiseMapper.toCruiseDto(savedCruise);
    }

    public CruiseResponse update(UUID id, UpdateCruiseRequest updateCruiseRequest) {
        Cruise cruiseToUpdate = cruiseRepository.findById(id)
                                                .orElseThrow(() -> new EntityNotFoundException("Cruise with id " + id + " not found"));
        cruiseMapper.updateCruiseFromDto(updateCruiseRequest, cruiseToUpdate);
        Cruise updatedCruise = cruiseRepository.save(cruiseToUpdate);
        return cruiseMapper.toCruiseDto(updatedCruise);
    }

    public void delete(UUID id) {
        cruiseRepository.deleteById(id);
    }
}
