package com.esaillog.sailor;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("sailors")
public class SailorController {
    private final SailorService sailorService;
    private final SailorMapper sailorMapper;

    @GetMapping
    public ResponseEntity<List<SailorDto>> getAll() {
        List<SailorDto> sailors = sailorService.findAll().stream().map(sailorMapper::toSailorDto).toList();
        return ResponseEntity.ok(sailors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SailorDto> getById(@PathVariable UUID id) {
        Sailor sailor = sailorService.findById(id);
        SailorDto sailorDto = sailorMapper.toSailorDto(sailor);
        return ResponseEntity.ok(sailorDto);
    }

    @PostMapping()
    public ResponseEntity<SailorDto> create(@RequestBody SailorDto sailorDto) {
        Sailor sailor = sailorMapper.toSailor(sailorDto);
        Sailor savedSailor = sailorService.save(sailor);
        SailorDto savedSailorDto = sailorMapper.toSailorDto(savedSailor);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedSailor.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedSailorDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SailorDto> update(@PathVariable UUID id, @RequestBody SailorDto sailorDto) {
        SailorDto updatedDto = sailorMapper.toSailorDto(sailorService.update(id, sailorMapper.toSailor(sailorDto)));
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        sailorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
