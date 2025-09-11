package com.esaillog.cruise;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("cruises")
public class CruiseController {
    private final CruiseService cruiseService;
    private final CruiseMapper cruiseMapper;

    @GetMapping
    public ResponseEntity<List<CruiseDto>> getAll() {
        List<CruiseDto> cruises = cruiseService.findAll().stream().map(cruiseMapper::toCruiseDto).toList();
        return ResponseEntity.ok(cruises);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CruiseDto> getById(@PathVariable UUID id) {
        Cruise cruise = cruiseService.findById(id);
        CruiseDto cruiseDto = cruiseMapper.toCruiseDto(cruise);
        return ResponseEntity.ok(cruiseDto);
    }

    @PostMapping()
    public ResponseEntity<CruiseDto> create(@RequestBody CruiseDto cruiseDto) {
        Cruise cruise = cruiseMapper.toCruise(cruiseDto);
        Cruise savedCruise = cruiseService.save(cruise);
        CruiseDto savedDto = cruiseMapper.toCruiseDto(savedCruise);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCruise.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CruiseDto> update(@PathVariable UUID id, @RequestBody CruiseDto cruiseDto) {
        CruiseDto updatedDto = cruiseMapper.toCruiseDto(cruiseService.update(id, cruiseMapper.toCruise(cruiseDto)));
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        cruiseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
