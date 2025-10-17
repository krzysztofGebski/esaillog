package com.esaillog.sailboat;

import com.esaillog.error.ResourceNotFoundException;
import com.esaillog.sailboat.dtos.CreateSailboatRequest;
import com.esaillog.sailboat.dtos.SailboatResponse;
import com.esaillog.sailboat.dtos.UpdateSailboatRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SailboatService {
    private final SailboatRepository sailboatRepository;
    private final SailboatMapper sailboatMapper;


    public List<SailboatResponse> findAll() {
        return sailboatRepository.findAll()
                                 .stream()
                                 .map(sailboatMapper::toSailboatDto)
                                 .toList();
    }

    public SailboatResponse findById(UUID id) {
        Sailboat sailboat = sailboatRepository.findById(id)
                                              .orElseThrow(() -> new ResourceNotFoundException("Sailboat", "id", id));
        return sailboatMapper.toSailboatDto(sailboat);
    }

    @Transactional
    public SailboatResponse save(CreateSailboatRequest createSailboatRequest) {
        Sailboat sailboat = sailboatMapper.createSailboatFromDto(createSailboatRequest);
        Sailboat savedSailboat = sailboatRepository.save(sailboat);
        return sailboatMapper.toSailboatDto(savedSailboat);
    }

    @Transactional
    public SailboatResponse update(UUID id, UpdateSailboatRequest updateSailboatRequest) {
        Sailboat sailboatToUpdate = sailboatRepository.findById(id)
                                                      .orElseThrow(() -> new ResourceNotFoundException("Sailboat", "id", id));

        sailboatToUpdate.update(updateSailboatRequest);

        return sailboatMapper.toSailboatDto(sailboatToUpdate);
    }

    @Transactional
    public void delete(UUID id) {
        sailboatRepository.deleteById(id);
    }
}
