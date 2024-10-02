package com.esaillog.sailor;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("sailors")
public class SailorController {
    private final SailorService sailorService;
    private final SailorMapper sailorMapper;

    @GetMapping
    public List<SailorDto> getAll() {
        return sailorService.findAll().stream().map(sailorMapper::toSailorDto).toList();
    }

    @GetMapping("/{id}")
    public SailorDto getById(@PathVariable String id) {
        Sailor sailor = sailorService.findById(UUID.fromString(id));
        return sailorMapper.toSailorDto(sailor);
    }

    @PostMapping()
    public SailorDto create(@RequestBody SailorDto sailorDto) {
        Sailor sailor = sailorMapper.toSailor(sailorDto);
        return sailorMapper.toSailorDto(sailorService.save(sailor));
    }

    @PutMapping("/{id}")
    public SailorDto update(@PathVariable String id, @RequestBody SailorDto sailorDto) {
        Sailor sailor = sailorService.findById(UUID.fromString(id));
        sailor.setFirstName(sailorDto.firstName());
        sailor.setLastName(sailorDto.lastName());
        sailor.setEmail(sailorDto.email());
        return sailorMapper.toSailorDto(sailorService.update(UUID.fromString(id), sailor));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        sailorService.delete(UUID.fromString(id));
    }
}
