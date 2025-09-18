package com.esaillog.sailor;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.esaillog.sailor.dtos.CreateSailorRequest;
import com.esaillog.sailor.dtos.SailorResponse;
import com.esaillog.sailor.dtos.UpdateSailorRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("sailors")
public class SailorController {
    private final SailorService sailorService;

    @GetMapping
    public ResponseEntity<List<SailorResponse>> getAll() {
        return ResponseEntity.ok(sailorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SailorResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(sailorService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<SailorResponse> create(@Valid @RequestBody CreateSailorRequest createSailorRequest) {
        SailorResponse savedSailor = sailorService.save(createSailorRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedSailor.id())
                .toUri();

        return ResponseEntity.created(location).body(savedSailor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SailorResponse> update(@PathVariable UUID id,
            @Valid @RequestBody UpdateSailorRequest updateSailorRequest) {
        return ResponseEntity.ok(sailorService.update(id, updateSailorRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        sailorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
