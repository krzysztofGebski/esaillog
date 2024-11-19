package com.esaillog.sailboat;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class SailboatRepository {
    private final List<Sailboat> sailboats = new ArrayList<>();

    public List<Sailboat> findAll() {
        return sailboats;
    }
    public Sailboat findById(UUID id) {
        return sailboats.stream().filter(sailboat -> sailboat.getId().equals(id)).findFirst().orElseThrow();
    }
    public Sailboat save(Sailboat sailboat) {
        Sailboat savedSailboat = new Sailboat(UUID.randomUUID(), sailboat.getName(), sailboat.getRegisterNumber(), sailboat.getType()
                , sailboat.getHomePort(), sailboat.getLength(), sailboat.getEngineKW());
        sailboats.add(savedSailboat);
        return savedSailboat;
    }
    public Sailboat update(UUID id, Sailboat sailboat) {
        Sailboat updatedSailboat = findById(id);
        updatedSailboat.setName(sailboat.getName());
        updatedSailboat.setRegisterNumber(sailboat.getRegisterNumber());
        updatedSailboat.setType(sailboat.getType());
        updatedSailboat.setHomePort(sailboat.getHomePort());
        updatedSailboat.setLength(sailboat.getLength());
        updatedSailboat.setEngineKW(sailboat.getEngineKW());
        return updatedSailboat;
    }

    public void delete(UUID id) {
        sailboats.removeIf(sailboat -> sailboat.getId().equals(id));
    }
}
