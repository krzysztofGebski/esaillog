package com.esaillog.sailor;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class SailorService {

    private List<Sailor> sailors;

    public SailorService() {
        this.sailors = List.of(
                new Sailor(UUID.fromString("0442949d-7db8-4a70-a167-e7c258d7692a"), "John", "Doe", "jon@sailor.com"),
                new Sailor(UUID.fromString("970dff9a-c407-4d90-8ba4-14f6a10207ac"), "Jane", "Doe", "jane@sailor.com"),
                new Sailor(UUID.fromString("c8b6ca8d-0731-42e6-81ab-85eae0014364"), "Jack", "Doe", "jack@sailor.com"));
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
