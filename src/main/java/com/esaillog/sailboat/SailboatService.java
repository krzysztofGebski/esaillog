package com.esaillog.sailboat;

import com.esaillog.error.EntityNotFoundException;

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
        return sailboatRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Sailboat not found with id: " + id));
    }

    public Sailboat save(Sailboat sailboat) {
        return sailboatRepository.save(sailboat);
    }

    public Sailboat update(UUID id, Sailboat updatedSailboat) {
        return sailboatRepository.findById(id)
                .map(existingSailboat -> {
                    updatedSailboat.setId(existingSailboat.getId());
                    return sailboatRepository.save(updatedSailboat);
                })
                .orElseThrow(() -> new EntityNotFoundException("Sailboat not found with id: " + id));
    }

    public void delete(UUID id) {
        sailboatRepository.deleteById(id);
    }
}
