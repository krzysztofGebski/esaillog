package com.esaillog.sailboat;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esaillog.error.EntityNotFoundException;
import com.esaillog.port.Port;
import com.esaillog.port.PortRepository;
import com.esaillog.sailboat.dtos.CreateSailboatRequest;
import com.esaillog.sailboat.dtos.SailboatResponse;
import com.esaillog.sailboat.dtos.UpdateSailboatRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SailboatService {
    private final SailboatRepository sailboatRepository;
    private final PortRepository portRepository;
    private final SailboatMapper sailboatMapper;

    @Transactional(readOnly = true)
    public List<SailboatResponse> findAll() {
       return sailboatRepository.findAll().stream()
               .map(sailboatMapper::toSailboatDto)
               .toList();
    }

    @Transactional(readOnly = true)
    public SailboatResponse findById(UUID id) {
        Sailboat sailboat = sailboatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sailboat not found with id: " + id));
        return sailboatMapper.toSailboatDto(sailboat);
     }

    @Transactional
    public SailboatResponse save(CreateSailboatRequest createSailboatRequest) {
        Sailboat sailboat = sailboatMapper.createSailboatFromDto(createSailboatRequest);
        Port homePort = portRepository.findById(createSailboatRequest.homePortId())
                .orElseThrow(() -> new EntityNotFoundException("Port not found with id: " + createSailboatRequest.homePortId()));
        sailboat.setHomePort(homePort);
        Sailboat savedSailboat = sailboatRepository.save(sailboat);
        return sailboatMapper.toSailboatDto(savedSailboat);
    }

    @Transactional
    public SailboatResponse update(UUID id, UpdateSailboatRequest updateSailboatRequest) {
        Sailboat sailboat = sailboatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sailboat not found with id: " + id));
        sailboatMapper.updateSailboatFromDto(updateSailboatRequest, sailboat);
        if (updateSailboatRequest.homePortId() != null) {
            Port newHomePort = portRepository.findById(updateSailboatRequest.homePortId())
                    .orElseThrow(() -> new EntityNotFoundException("Port not found with id: " + updateSailboatRequest.homePortId()));
            sailboat.setHomePort(newHomePort);
        }
        Sailboat updatedSailboat = sailboatRepository.save(sailboat);
        return sailboatMapper.toSailboatDto(updatedSailboat);
    }

    public void delete(UUID id) {
        sailboatRepository.deleteById(id);
    }
}
