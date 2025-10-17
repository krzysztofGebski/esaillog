package com.esaillog.port;

import com.esaillog.port.dtos.CreatePortRequest;
import com.esaillog.port.dtos.PortResponse;
import com.esaillog.port.dtos.UpdatePortRequest;
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
@RequestMapping(value = "/ports", produces = "application/vnd.esaillog.v1+json")
public class PortController {
    private final PortService portService;

    /**
     * Retrieves all ports.
     *
     * @return a ResponseEntity containing a list of PortResponse objects.
     */
    @GetMapping
    public ResponseEntity<List<PortResponse>> getAll() {
        return ResponseEntity.ok(portService.findAll());
    }

    /**
     * Retrieves a port by its ID.
     *
     * @param id the ID of the port to retrieve.
     * @return a ResponseEntity containing the PortResponse object.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PortResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(portService.findById(id));
    }

    /**
     * Creates a new port.
     *
     * @param createPortRequest the request object containing the details of the port to create.
     * @return a ResponseEntity containing the created PortResponse object.
     */
    @PostMapping
    public ResponseEntity<PortResponse> create(@Valid @RequestBody CreatePortRequest createPortRequest) {
        PortResponse savedPort = portService.save(createPortRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                                  .path("/{id}")
                                                  .buildAndExpand(savedPort.id())
                                                  .toUri();

        return ResponseEntity.created(location)
                             .body(savedPort);
    }

    /**
     * Updates an existing port.
     *
     * @param id                the ID of the port to update.
     * @param updatePortRequest the request object containing the updated details of the port.
     * @return a ResponseEntity containing the updated PortResponse object.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PortResponse> update(@PathVariable UUID id, @Valid @RequestBody UpdatePortRequest updatePortRequest) {
        return ResponseEntity.ok(portService.update(id, updatePortRequest));
    }

    /**
     * Deletes a port by its ID.
     *
     * @param id the ID of the port to delete.
     * @return a ResponseEntity with no content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        portService.delete(id);
        return ResponseEntity.noContent()
                             .build();
    }
}
