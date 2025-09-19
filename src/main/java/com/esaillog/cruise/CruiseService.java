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

    public List<CruiseResponse> findAll() {
        return List.of();
    }

    public CruiseResponse findById(UUID id) {
        return null;
    }

    public CruiseResponse save(CreateCruiseRequest createCruiseRequest) {
        return null;
    }

    public CruiseResponse update(UUID id, UpdateCruiseRequest updateCruiseRequest) {
        return null;
    }

    public void delete(UUID id) {
        cruiseRepository.deleteById(id);
    }
}
