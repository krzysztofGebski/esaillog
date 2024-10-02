package com.esaillog.sailor;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class SailorRepository {
    private final List<Sailor> sailors = new ArrayList<>();

    public List<Sailor> findAll() {
        return sailors;
    }

    public Sailor findById(UUID id) {
        return sailors.stream().filter(sailor -> sailor.getId().equals(id)).findFirst().orElseThrow();
    }

    public Sailor save(Sailor sailor) {
        Sailor savedSailor = new Sailor(UUID.randomUUID(), sailor.getFirstName(), sailor.getLastName(), sailor.getEmail());
        sailors.add(savedSailor);
        return savedSailor;
    }

    public Sailor update(UUID id, Sailor sailor) {
        Sailor updatedSailor = findById(id);
        updatedSailor.setFirstName(sailor.getFirstName());
        updatedSailor.setLastName(sailor.getLastName());
        updatedSailor.setEmail(sailor.getEmail());
        return updatedSailor;
    }

    public void delete(UUID id) {
        sailors.removeIf(sailor -> sailor.getId().equals(id));
    }
}
