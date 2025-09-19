package com.esaillog.sailboat;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.esaillog.sailboat.dtos.CreateSailboatRequest;
import com.esaillog.sailboat.dtos.SailboatResponse;
import com.esaillog.sailboat.dtos.UpdateSailboatRequest;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("sailboats")
public class SailboatController {
    private final SailboatService sailboatService;

    @GetMapping
    public ResponseEntity<List<SailboatResponse>> getAll() {
        return ResponseEntity.ok(sailboatService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SailboatResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(sailboatService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<SailboatResponse> create(@Valid @RequestBody CreateSailboatRequest createSailboatRequest) {
        SailboatResponse savedSailboat = sailboatService.save(createSailboatRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedSailboat.id())
                .toUri();
        return ResponseEntity.created(location).body(savedSailboat);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SailboatResponse> update(@PathVariable UUID id, @Valid @RequestBody UpdateSailboatRequest updateSailboatRequest) {
        return ResponseEntity.ok(sailboatService.update(id, updateSailboatRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        sailboatService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
