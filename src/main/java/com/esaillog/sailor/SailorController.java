package com.esaillog.sailor;

import com.esaillog.sailor.dtos.CreateSailorRequest;
import com.esaillog.sailor.dtos.SailorResponse;
import com.esaillog.sailor.dtos.UpdateSailorRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing sailors.
 * Provides endpoints for CRUD operations on {@link Sailor} entities.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/sailors", produces = "application/vnd.esaillog.v1+json")
public class SailorController {
    private final SailorService sailorService;

    /**
     * Retrieves a list of all sailors.
     *
     * @return a {@link ResponseEntity} containing a list of all sailors.
     */
    @GetMapping
    public ResponseEntity<List<SailorResponse>> getAll() {
        return ResponseEntity.ok(sailorService.findAll());
    }

    /**
     * Retrieves a specific sailor by their ID.
     *
     * @param id the UUID of the sailor to retrieve.
     * @return a {@link ResponseEntity} containing the sailor's data.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SailorResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(sailorService.findById(id));
    }

    /**
     * Creates a new sailor.
     *
     * @param createSailorRequest the request body containing the new sailor's data.
     * @return a {@link ResponseEntity} with a 201 Created status, including the location of the
     * new resource in the headers and the created sailor's data in the body.
     */
    @PostMapping
    public ResponseEntity<SailorResponse> create(@Valid @RequestBody CreateSailorRequest createSailorRequest) {
        SailorResponse savedSailor = sailorService.save(createSailorRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                                  .path("/{id}")
                                                  .buildAndExpand(savedSailor.id())
                                                  .toUri();

        return ResponseEntity.created(location)
                             .body(savedSailor);
    }

    /**
     * Updates an existing sailor.
     *
     * @param id                  the UUID of the sailor to update.
     * @param updateSailorRequest the request body containing the updated data.
     * @return a {@link ResponseEntity} containing the updated sailor's data.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SailorResponse> update(@PathVariable UUID id, @Valid @RequestBody UpdateSailorRequest updateSailorRequest) {
        return ResponseEntity.ok(sailorService.update(id, updateSailorRequest));
    }

    /**
     * Deletes a sailor by their ID.
     *
     * @param id the UUID of the sailor to delete.
     * @return a {@link ResponseEntity} with a 204 No Content status.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        sailorService.delete(id);
        return ResponseEntity.noContent()
                             .build();
    }
}
