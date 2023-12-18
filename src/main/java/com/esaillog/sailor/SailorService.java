package com.esaillog.sailor;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SailorService {

    private final SailorRepository sailorRepository;


    public List<Sailor> getSailors() {
        return sailorRepository.findAll();
    }

    public Sailor getSailor(UUID uuid) {
        return sailorRepository.findById(uuid).orElseThrow();
    }

    public void addSailor(Sailor sailor) {
        sailorRepository.save(sailor);
    }

    public void deleteSailor(UUID uuid) {
        sailorRepository.deleteById(uuid);
    }

    public void updateSailor(UUID uuid, Sailor sailor) {
        Sailor sailorToUpdate = getSailor(uuid);
        sailorToUpdate.setFirstName(sailor.getFirstName());
        sailorToUpdate.setLastName(sailor.getLastName());
        sailorToUpdate.setEmail(sailor.getEmail());
    }
}
