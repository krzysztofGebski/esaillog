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
    public ResponseEntity<List<Sailor>> getSailors() {
        return ResponseEntity.ok(sailorService.getSailors());
    }

    @GetMapping("{uuid}")
    public ResponseEntity<Sailor> getSailor(@PathVariable String uuid) {
        return ResponseEntity.ok(sailorService.getSailor(UUID.fromString(uuid)));
    }

    @PostMapping
    public ResponseEntity<Void> addSailor(@RequestBody Sailor sailor) {
        sailorService.addSailor(sailor);
        return ResponseEntity.ok().build();
    }

    @PostMapping("{uuid}")
    public ResponseEntity<Void> updateSailor(@PathVariable String uuid, @RequestBody Sailor sailor) {
        sailorService.updateSailor(UUID.fromString(uuid), sailor);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{uuid}")
    public ResponseEntity<Void> deleteSailor(@PathVariable String uuid) {
        sailorService.deleteSailor(UUID.fromString(uuid));
        return ResponseEntity.noContent().build();
    }

}
