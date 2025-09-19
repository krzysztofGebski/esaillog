package com.esaillog.sailboat;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.esaillog.sailboat.dtos.CreateSailboatRequest;
import com.esaillog.sailboat.dtos.SailboatResponse;
import com.esaillog.sailboat.dtos.UpdateSailboatRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SailboatService {
    private final SailboatRepository sailboatRepository;

    public List<SailboatResponse> findAll() {
       return List.of(); 
    }

    public SailboatResponse findById(UUID id) {
        return null;
    }

    public SailboatResponse save(CreateSailboatRequest createSailboatRequest) {
        return null;
    }

    public SailboatResponse update(UUID id, UpdateSailboatRequest updateSailboatRequest) {
        return null;
    }

    public void delete(UUID id) {
        sailboatRepository.deleteById(id);
    }
}
