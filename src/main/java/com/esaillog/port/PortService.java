package com.esaillog.port;

import com.esaillog.error.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PortService {
    private final PortRepository portRepository;

    public List<Port> findAll() {
        return portRepository.findAll();
    }

    public Port findById(UUID id) {
        return portRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Port not found with id: " + id));
    }

    public Port save(Port port) {
        return portRepository.save(port);
    }

    public Port update(UUID id, Port updatedPort) {
        return portRepository.findById(id)
                .map(existingPort -> {
                    updatedPort.setId(existingPort.getId());
                    return portRepository.save(updatedPort);
                })
                .orElseThrow(() -> new EntityNotFoundException("Port not found with id: " + id));
    }

    public void delete(UUID id) {
        portRepository.deleteById(id);
    }
}
