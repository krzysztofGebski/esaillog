package com.esaillog.sailboat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SailboatService {
    private final SailboatRepository sailboatRepository;

    public List<Sailboat> findAll() {
        return sailboatRepository.findAll();
    }

    public Sailboat findById(UUID id) {
        return sailboatRepository.findById(id).orElseThrow();
    }

    public Sailboat save(Sailboat sailboat) {
        return sailboatRepository.save(sailboat);
    }

    public Sailboat update(UUID id, Sailboat updatedSailboat) {
        Sailboat existingSailboat = sailboatRepository.findById(id)
                                                      .orElseThrow(() -> new IllegalArgumentException("Sailboat not " + "found"));

        existingSailboat.setName(updatedSailboat.getName());
        existingSailboat.setRegisterNumber(updatedSailboat.getRegisterNumber());
        existingSailboat.setType(updatedSailboat.getType());
        existingSailboat.setHomePort(updatedSailboat.getHomePort());
        existingSailboat.setLength(updatedSailboat.getLength());
        existingSailboat.setEngineKW(updatedSailboat.getEngineKW());
        existingSailboat.setCruises(updatedSailboat.getCruises());
        return sailboatRepository.save(existingSailboat);
    }

    public void delete(UUID id) {
        sailboatRepository.deleteById(id);
    }
}
