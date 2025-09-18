package com.esaillog.port;

import com.esaillog.error.EntityNotFoundException;
import com.esaillog.port.dtos.CreatePortRequest;
import com.esaillog.port.dtos.PortResponse;
import com.esaillog.port.dtos.UpdatePortRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PortService {
    private final PortRepository portRepository;
    private final PortMapper portMapper;

    public List<PortResponse> findAll() {
        return portRepository.findAll().stream().map(portMapper::toPortDto).toList();        
    }

    public PortResponse findById(UUID id) {    
        Port port = portRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Port not found with id: " + id));
        return portMapper.toPortDto(port);
    }

    public PortResponse save(CreatePortRequest createPortRequest) {
        Port port = portMapper.createPortFromDto(createPortRequest);
        Port savedPort = portRepository.save(port);
        return portMapper.toPortDto(savedPort);
    }

    public PortResponse update(UUID id, UpdatePortRequest updatePortRequest) {
        Port existingPort = portRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Port not found with id: " + id));
        portMapper.updatePortFromDto(updatePortRequest, existingPort);
        Port updatedPort = portRepository.save(existingPort);
        return portMapper.toPortDto(updatedPort);
    }

    public void delete(UUID id) {
        portRepository.deleteById(id);
    }
}
