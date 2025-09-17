package com.esaillog.sailor;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.esaillog.sailor.dtos.CreateSailorRequest;
import com.esaillog.sailor.dtos.SailorResponse;
import com.esaillog.sailor.dtos.UpdateSailorRequest;

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
    public ResponseEntity<List<SailorResponse>> getAll() {
        List<SailorResponse> sailors = sailorService.findAll().stream().map(sailorMapper::toSailorDto).toList();
        return ResponseEntity.ok(sailors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SailorResponse> getById(@PathVariable UUID id) {
        Sailor sailor = sailorService.findById(id);
        SailorResponse sailorDto = sailorMapper.toSailorDto(sailor);
        return ResponseEntity.ok(sailorDto);
    }

    @PostMapping()
    public ResponseEntity<SailorResponse> create(@Valid @RequestBody CreateSailorRequest createSailorRequest) {
        Sailor sailor = sailorMapper.createSailorFromDto(createSailorRequest);
        Sailor savedSailor = sailorService.save(sailor);
        SailorResponse savedSailorDto = sailorMapper.toSailorDto(savedSailor);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedSailor.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedSailorDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SailorResponse> update(@PathVariable UUID id, @Valid @RequestBody UpdateSailorRequest updateSailorRequest) {
        Sailor sailorToUpdate = sailorService.findById(id);
        sailorMapper.updateSailorFromDto(updateSailorRequest, sailorToUpdate);
        Sailor updatedSailor = sailorService.save(sailorToUpdate);
        SailorResponse updatedDto = sailorMapper.toSailorDto(updatedSailor);
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        sailorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
