package com.esaillog.sailboat;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.esaillog.sailboat.dtos.SailboatResponse;

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
    public ResponseEntity<List<SailboatResponse>> getAll() {
        List<SailboatResponse> sailboats = sailboatService.findAll().stream().map(sailboatMapper::toSailboatDto).toList();
        return ResponseEntity.ok(sailboats);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SailboatResponse> getById(@PathVariable UUID id) {
        Sailboat sailboat = sailboatService.findById(id);
        SailboatResponse sailboatDto = sailboatMapper.toSailboatDto(sailboat);
        return ResponseEntity.ok(sailboatDto);
    }

    @PostMapping()
    public ResponseEntity<SailboatResponse> create(@Valid @RequestBody SailboatResponse sailboatDto) {
        Sailboat sailboat = sailboatMapper.toSailboat(sailboatDto);
        Sailboat savedSailboat = sailboatService.save(sailboat);
        SailboatResponse savedDto = sailboatMapper.toSailboatDto(savedSailboat);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedSailboat.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SailboatResponse> update(@PathVariable UUID id, @Valid @RequestBody SailboatResponse sailboatDto) {
        SailboatResponse updatedDto = sailboatMapper.toSailboatDto(sailboatService.update(id, sailboatMapper.toSailboat(sailboatDto)));
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        sailboatService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
