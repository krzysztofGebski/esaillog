package com.esaillog.port;

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

import com.esaillog.port.dtos.CreatePortRequest;
import com.esaillog.port.dtos.PortResponse;
import com.esaillog.port.dtos.UpdatePortRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("ports")
public class PortController {
    private final PortService portService;

    @GetMapping
    public ResponseEntity<List<PortResponse>> getAll() {
        return ResponseEntity.ok(portService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PortResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(portService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<PortResponse> create(@Valid @RequestBody CreatePortRequest createPortRequest) {
        PortResponse savedPort = portService.save(createPortRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPort.id())
                .toUri();

        return ResponseEntity.created(location).body(savedPort);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PortResponse> update(@PathVariable UUID id, @Valid @RequestBody UpdatePortRequest updatePortRequest) {
        return ResponseEntity.ok(portService.update(id, updatePortRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        portService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
