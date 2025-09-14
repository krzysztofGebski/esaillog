package com.esaillog.cruise;

import com.esaillog.error.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CruiseService {
    private final CruiseRepository cruiseRepository;

    public List<Cruise> findAll() {
        return cruiseRepository.findAll();
    }

    public Cruise findById(UUID id) {
        return cruiseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cruise not found with id: " + id));
    }

    public Cruise save(Cruise cruise) {
        return cruiseRepository.save(cruise);
    }

    public Cruise update(UUID id, Cruise updatedCruise) {
        return cruiseRepository.findById(id)
                .map(existingCruise -> {
                    updatedCruise.setId(existingCruise.getId());
                    return cruiseRepository.save(updatedCruise);
                })
                .orElseThrow(() -> new EntityNotFoundException("Cruise not found with id: " + id));
    }

    public void delete(UUID id) {
        cruiseRepository.deleteById(id);
    }
}
