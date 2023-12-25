package com.esaillog.training;

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
@RequestMapping("/trainings")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;

    @GetMapping
    public ResponseEntity<List<Training>> getTrainings() {
        return ResponseEntity.ok(trainingService.getTrainings());
    }

    @GetMapping("{uuid}")
    public ResponseEntity<Training> getTraining(@PathVariable String uuid) {
        return ResponseEntity.ok(trainingService.getTraining(UUID.fromString(uuid)));
    }

    @PostMapping
    public ResponseEntity<Void> addTraining(@RequestBody Training training) {
        trainingService.addTraining(training);
        return ResponseEntity.ok().build();
    }

    @PostMapping("{uuid}")
    public ResponseEntity<Void> updateTraining(@PathVariable String uuid, @RequestBody Training training) {
        trainingService.updateTraining(UUID.fromString(uuid), training);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{uuid}")
    public ResponseEntity<Void> deleteTraining(@PathVariable String uuid) {
        trainingService.deleteTraining(UUID.fromString(uuid));
        return ResponseEntity.noContent().build();
    }

}
