package com.esaillog.sailor;

import com.esaillog.error.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SailorService {
    private final SailorRepository sailorRepository;

    public List<Sailor> findAll() {
        return sailorRepository.findAll();
    }

    public Sailor findById(UUID id) {
        return sailorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Sailor not found with id: " + id));
    }

    public Sailor save(Sailor sailor) {
        return sailorRepository.save(sailor);
    }

    public Sailor update(UUID id, Sailor updatedSailor) {
        return sailorRepository.findById(id)
                .map(existingSailor -> {
                    updatedSailor.setId(existingSailor.getId());
                    return sailorRepository.save(updatedSailor);
                })
                .orElseThrow(() -> new EntityNotFoundException("Sailor not found with id: " + id));
    }

    public void delete(UUID id) {
        sailorRepository.deleteById(id);
    }
}
