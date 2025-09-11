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
    public List<CruiseDto> getAll() {
        return cruiseService.findAll().stream().map(cruiseMapper::toCruiseDto).toList();
    }

    @GetMapping("/{id}")
    public CruiseDto getById(@PathVariable UUID id) {
        Cruise cruise = cruiseService.findById(id);
        return cruiseMapper.toCruiseDto(cruise);
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
    public CruiseDto update(@PathVariable UUID id, @RequestBody CruiseDto cruiseDto) {
        return cruiseMapper.toCruiseDto(cruiseService.update(id, cruiseMapper.toCruise(cruiseDto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        cruiseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
