package com.esaillog.port;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.UUID.fromString;

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
    public PortDto getById(@PathVariable String id) {
        Port port = portService.findById(fromString(id));
        return portMapper.toPortDto(port);
    }

    @PostMapping()
    public PortDto create(@RequestBody PortDto portDto) {
        Port port = portMapper.toPort(portDto);
        return portMapper.toPortDto(portService.save(port));
    }

    @PutMapping("/{id}")
    public PortDto update(@PathVariable String id, @RequestBody PortDto portDto) {
        return portMapper.toPortDto(portService.update(fromString(id), portMapper.toPort(portDto)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        portService.delete(fromString(id));
    }
}
