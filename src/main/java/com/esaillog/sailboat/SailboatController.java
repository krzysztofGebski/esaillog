package com.esaillog.sailboat;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("sailboats")
public class SailboatController {
    private final SailboatService sailboatService;
    private final SailboatMapper sailboatMapper;

    @GetMapping
    public List<SailboatDto> getAll() {
        return sailboatService.findAll().stream().map(sailboatMapper::toSailboatDto).toList();
    }

    @GetMapping("/{id}")
    public SailboatDto getById(@PathVariable String id) {
        Sailboat sailboat = sailboatService.findById(UUID.fromString(id));
        return sailboatMapper.toSailboatDto(sailboat);
    }

    @PostMapping()
    public SailboatDto create(@RequestBody SailboatDto sailboatDto) {
        Sailboat sailboat = sailboatMapper.toSailboat(sailboatDto);
        return sailboatMapper.toSailboatDto(sailboatService.save(sailboat));
    }

    @PutMapping("/{id}")
    public SailboatDto update(@PathVariable String id, @RequestBody SailboatDto sailboatDto) {
        return sailboatMapper.toSailboatDto(sailboatService.update(UUID.fromString(id),
                sailboatMapper.toSailboat(sailboatDto)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        sailboatService.delete(UUID.fromString(id));
    }
}
