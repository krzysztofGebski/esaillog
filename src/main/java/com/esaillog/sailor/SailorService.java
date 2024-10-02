package com.esaillog.sailor;

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
        return sailorRepository.findById(id);
    }
    public Sailor save(Sailor sailor) {
        return sailorRepository.save(sailor);
    }

    public Sailor update(UUID id, Sailor sailor) {
        return sailorRepository.update(id, sailor);
    }
    public void delete(UUID id) {
        sailorRepository.delete(id);
    }
}
