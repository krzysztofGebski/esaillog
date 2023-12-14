package com.esaillog.sailor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class SailorService {

    private List<Sailor> sailors = new ArrayList<>();

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
        sailor.setId(UUID.randomUUID());
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
