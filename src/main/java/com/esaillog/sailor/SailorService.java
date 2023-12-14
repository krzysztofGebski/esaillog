package com.esaillog.sailor;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class SailorService {

    private List<Sailor> sailors;

    public SailorService() {
        this.sailors = List.of(
                new Sailor(UUID.randomUUID(), "John", "Doe", "jon@sailor.com"),
                new Sailor(UUID.randomUUID(), "Jane", "Doe", "jane@sailor.com"),
                new Sailor(UUID.randomUUID(), "Jack", "Doe", "jack@sailor.com"));
    }

    public List<Sailor> getSailors() {
        return sailors;
    }

    public Sailor getSailor(UUID uuid) {
        return sailors.stream()
                .filter(sailor -> sailor.getId().equals(uuid))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Sailor " + uuid + " does not exist"));
    }

    public void addSailor(Sailor sailor) {
        sailors.add(sailor);
    }

    public void deleteSailor(UUID uuid) {
        sailors.removeIf(sailor -> sailor.getId().equals(uuid));
    }

    public void updateSailor(UUID uuid, Sailor sailor) {
        Sailor sailorToUpdate = getSailor(uuid);
        sailorToUpdate.setFirstName(sailor.getFirstName());
        sailorToUpdate.setLastName(sailor.getLastName());
        sailorToUpdate.setEmail(sailor.getEmail());
    }
}
