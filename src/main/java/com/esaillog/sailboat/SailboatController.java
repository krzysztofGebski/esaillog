package com.esaillog.sailboat;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
    public SailboatDto getById(@PathVariable UUID id) {
        Sailboat sailboat = sailboatService.findById(id);
        return sailboatMapper.toSailboatDto(sailboat);
    }

    @PostMapping()
    public ResponseEntity<SailboatDto> create(@RequestBody SailboatDto sailboatDto) {
        Sailboat sailboat = sailboatMapper.toSailboat(sailboatDto);
        Sailboat savedSailboat = sailboatService.save(sailboat);
        SailboatDto savedDto = sailboatMapper.toSailboatDto(savedSailboat);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedSailboat.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedDto);
    }

    @PutMapping("/{id}")
    public SailboatDto update(@PathVariable UUID id, @RequestBody SailboatDto sailboatDto) {
        return sailboatMapper.toSailboatDto(sailboatService.update(id, sailboatMapper.toSailboat(sailboatDto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        sailboatService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
