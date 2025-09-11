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
        Port existingPort = portRepository.findById(id)
                                          .orElseThrow(() -> new EntityNotFoundException("Port not found with id: " + id));

        existingPort.setName(updatedPort.getName());
        existingPort.setDescription(updatedPort.getDescription());
        existingPort.setSailboats(updatedPort.getSailboats());
        existingPort.setCruises(updatedPort.getCruises());
        return portRepository.save(existingPort);
    }

    public void delete(UUID id) {
        portRepository.deleteById(id);
    }
}
