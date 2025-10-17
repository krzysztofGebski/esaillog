package com.esaillog.sailboat;

import com.esaillog.sailboat.dtos.CreateSailboatRequest;
import com.esaillog.sailboat.dtos.SailboatResponse;
import com.esaillog.sailboat.dtos.UpdateSailboatRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/sailboats", produces = "application/vnd.esaillog.v1+json")
public class SailboatController {
    private final SailboatService sailboatService;

    /**
     * Retrieves all sailboats.
     *
     * @return a ResponseEntity containing a list of SailboatResponse objects.
     */
    @GetMapping
    public ResponseEntity<List<SailboatResponse>> getAll() {
        return ResponseEntity.ok(sailboatService.findAll());
    }

    /**
     * Retrieves a sailboat by its ID.
     *
     * @param id the ID of the sailboat to retrieve.
     * @return a ResponseEntity containing the SailboatResponse object.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SailboatResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(sailboatService.findById(id));
    }

    /**
     * Creates a new sailboat.
     *
     * @param createSailboatRequest the request object containing the details of the sailboat to create.
     * @return a ResponseEntity containing the created SailboatResponse object.
     */
    @PostMapping
    public ResponseEntity<SailboatResponse> create(@Valid @RequestBody CreateSailboatRequest createSailboatRequest) {
        SailboatResponse savedSailboat = sailboatService.save(createSailboatRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedSailboat.id())
                .toUri();
        return ResponseEntity.created(location).body(savedSailboat);
    }

    /**
     * Updates an existing sailboat.
     *
     * @param id                    the ID of the sailboat to update.
     * @param updateSailboatRequest the request object containing the updated details of the sailboat.
     * @return a ResponseEntity containing the updated SailboatResponse object.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SailboatResponse> update(@PathVariable UUID id, @Valid @RequestBody UpdateSailboatRequest updateSailboatRequest) {
        return ResponseEntity.ok(sailboatService.update(id, updateSailboatRequest));
    }

    /**
     * Deletes a sailboat by its ID.
     *
     * @param id the ID of the sailboat to delete.
     * @return a ResponseEntity with no content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        sailboatService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
