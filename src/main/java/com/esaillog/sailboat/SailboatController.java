package com.esaillog.sailboat;

import jakarta.validation.Valid;
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
    public ResponseEntity<List<SailboatDto>> getAll() {
        List<SailboatDto> sailboats = sailboatService.findAll().stream().map(sailboatMapper::toSailboatDto).toList();
        return ResponseEntity.ok(sailboats);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SailboatDto> getById(@PathVariable UUID id) {
        Sailboat sailboat = sailboatService.findById(id);
        SailboatDto sailboatDto = sailboatMapper.toSailboatDto(sailboat);
        return ResponseEntity.ok(sailboatDto);
    }

    @PostMapping()
    public ResponseEntity<SailboatDto> create(@Valid @RequestBody SailboatDto sailboatDto) {
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
    public ResponseEntity<SailboatDto> update(@PathVariable UUID id, @Valid @RequestBody SailboatDto sailboatDto) {
        SailboatDto updatedDto = sailboatMapper.toSailboatDto(sailboatService.update(id, sailboatMapper.toSailboat(sailboatDto)));
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        sailboatService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
