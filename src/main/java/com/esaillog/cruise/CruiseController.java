package com.esaillog.cruise;

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

import com.esaillog.cruise.dtos.CreateCruiseRequest;
import com.esaillog.cruise.dtos.CruiseResponse;
import com.esaillog.cruise.dtos.UpdateCruiseRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("cruises")
public class CruiseController {
    private final CruiseService cruiseService;

    @GetMapping
    public ResponseEntity<List<CruiseResponse>> getAll() {
        return ResponseEntity.ok(cruiseService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CruiseResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(cruiseService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<CruiseResponse> create(@Valid @RequestBody CreateCruiseRequest createCruiseRequest) {
        CruiseResponse savedCrusie = cruiseService.save(createCruiseRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCrusie.id())
                .toUri();

        return ResponseEntity.created(location).body(savedCrusie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CruiseResponse> update(@PathVariable UUID id,
            @Valid @RequestBody UpdateCruiseRequest updateCruiseRequest) {
        return ResponseEntity.ok(cruiseService.update(id, updateCruiseRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        cruiseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
