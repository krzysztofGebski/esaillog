package com.esaillog.port;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("ports")
public class PortController {
    private final PortService portService;
    private final PortMapper portMapper;

    @GetMapping
    public List<PortDto> getAll() {
        return portService.findAll().stream().map(portMapper::toPortDto).toList();
    }

    @GetMapping("/{id}")
    public PortDto getById(@PathVariable UUID id) {
        Port port = portService.findById(id);
        return portMapper.toPortDto(port);
    }

    @PostMapping()
    public ResponseEntity<PortDto> create(@RequestBody PortDto portDto) {
        Port port = portMapper.toPort(portDto);
        Port savedPort = portService.save(port);
        PortDto savedDto = portMapper.toPortDto(savedPort);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPort.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedDto);
    }

    @PutMapping("/{id}")
    public PortDto update(@PathVariable UUID id, @RequestBody PortDto portDto) {
        return portMapper.toPortDto(portService.update(id, portMapper.toPort(portDto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        portService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
