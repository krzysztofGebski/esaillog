package com.esaillog.sailor;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.esaillog.error.EntityNotFoundException;
import com.esaillog.sailor.dtos.CreateSailorRequest;
import com.esaillog.sailor.dtos.SailorResponse;
import com.esaillog.sailor.dtos.UpdateSailorRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SailorService {
    private final SailorRepository sailorRepository;
    private final SailorMapper sailorMapper;

    public List<SailorResponse> findAll() {
        return sailorRepository.findAll().stream()
                .map(sailorMapper::toSailorDto)
                .toList();
    }

    public SailorResponse findById(UUID id) {
        Sailor sailor = sailorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sailor with id " + id + " not found"));
        return sailorMapper.toSailorDto(sailor);
    }

    public SailorResponse save(CreateSailorRequest createSailorRequest) {
        Sailor sailor = sailorMapper.createSailorFromDto(createSailorRequest);
        Sailor savedSailor = sailorRepository.save(sailor);
        return sailorMapper.toSailorDto(savedSailor);
    }

    public SailorResponse update(UUID id, UpdateSailorRequest updateSailorRequest) {
        Sailor sailorToUpdate = sailorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sailor with id " + id + " not found"));
        sailorMapper.updateSailorFromDto(updateSailorRequest, sailorToUpdate);
        Sailor updatedSailor = sailorRepository.save(sailorToUpdate);
        return sailorMapper.toSailorDto(updatedSailor);
    }

    public void delete(UUID id) {
        sailorRepository.deleteById(id);
    }
}
