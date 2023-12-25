package com.esaillog.sailor;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/sailors")
@RequiredArgsConstructor
public class SailorController {

    private final SailorService sailorService;


    @GetMapping
    public ResponseEntity<List<SailorDTO>> getSailors() {
        return ResponseEntity.ok(sailorService.getSailors().stream().map(SailorMapper::toSailorDTO).toList());
    }

    @GetMapping("{uuid}")
    public ResponseEntity<SailorDTO> getSailor(@PathVariable String uuid) {
        return ResponseEntity.ok(SailorMapper.toSailorDTO(sailorService.getSailor(UUID.fromString(uuid))));
    }

    @PostMapping
    public ResponseEntity<Void> addSailor(@RequestBody SailorDTO sailorDTO) {
        sailorService.addSailor(SailorMapper.toSailor(sailorDTO));
        return ResponseEntity.ok().build();
    }

    @PostMapping("{uuid}")
    public ResponseEntity<Void> updateSailor(@PathVariable String uuid, @RequestBody SailorDTO sailorDTO) {
        sailorService.updateSailor(UUID.fromString(uuid), SailorMapper.toSailor(sailorDTO));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{uuid}")
    public ResponseEntity<Void> deleteSailor(@PathVariable String uuid) {
        sailorService.deleteSailor(UUID.fromString(uuid));
        return ResponseEntity.noContent().build();
    }

}
