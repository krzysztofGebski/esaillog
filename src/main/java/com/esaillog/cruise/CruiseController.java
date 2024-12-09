package com.esaillog.cruise;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.UUID.fromString;

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
    public CruiseDto getById(@PathVariable String id) {
        Cruise cruise = cruiseService.findById(fromString(id));
        return cruiseMapper.toCruiseDto(cruise);
    }

    @PostMapping()
    public CruiseDto create(@RequestBody CruiseDto cruiseDto) {
        Cruise cruise = cruiseMapper.toCruise(cruiseDto);
        return cruiseMapper.toCruiseDto(cruiseService.save(cruise));
    }

    @PutMapping("/{id}")
    public CruiseDto update(@PathVariable String id, @RequestBody CruiseDto cruiseDto) {
        return cruiseMapper.toCruiseDto(cruiseService.update(fromString(id), cruiseMapper.toCruise(cruiseDto)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        cruiseService.delete(fromString(id));
    }
}
