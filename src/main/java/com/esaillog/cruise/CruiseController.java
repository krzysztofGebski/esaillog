package com.esaillog.cruise;

import com.esaillog.cruise.dtos.CreateCruiseRequest;
import com.esaillog.cruise.dtos.CruiseResponse;
import com.esaillog.cruise.dtos.UpdateCruiseRequest;
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
@RequestMapping(value = "/cruises", produces = "application/vnd.esaillog.v1+json")
public class CruiseController {
    private final CruiseService cruiseService;

    /**
     * Retrieves all cruises.
     *
     * @return a ResponseEntity containing a list of CruiseResponse objects.
     */
    @GetMapping
    public ResponseEntity<List<CruiseResponse>> getAll() {
        return ResponseEntity.ok(cruiseService.findAll());
    }

    /**
     * Retrieves a cruise by its ID.
     *
     * @param id the ID of the cruise to retrieve.
     * @return a ResponseEntity containing the CruiseResponse object.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CruiseResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(cruiseService.findById(id));
    }

    /**
     * Creates a new cruise.
     *
     * @param createCruiseRequest the request object containing the details of the cruise to create.
     * @return a ResponseEntity containing the created CruiseResponse object.
     */
    @PostMapping
    public ResponseEntity<CruiseResponse> create(@Valid @RequestBody CreateCruiseRequest createCruiseRequest) {
        CruiseResponse savedCrusie = cruiseService.save(createCruiseRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                                  .path("/{id}")
                                                  .buildAndExpand(savedCrusie.id())
                                                  .toUri();

        return ResponseEntity.created(location)
                             .body(savedCrusie);
    }

    /**
     * Updates an existing cruise.
     *
     * @param id                  the ID of the cruise to update.
     * @param updateCruiseRequest the request object containing the updated details of the cruise.
     * @return a ResponseEntity containing the updated CruiseResponse object.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CruiseResponse> update(@PathVariable UUID id, @Valid @RequestBody UpdateCruiseRequest updateCruiseRequest) {
        return ResponseEntity.ok(cruiseService.update(id, updateCruiseRequest));
    }

    /**
     * Deletes a cruise by its ID.
     *
     * @param id the ID of the cruise to delete.
     * @return a ResponseEntity with no content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        cruiseService.delete(id);
        return ResponseEntity.noContent()
                             .build();
    }
}
