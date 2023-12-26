package com.esaillog.training;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/trainings")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingMapper trainingMapper;

    @GetMapping
    public ResponseEntity<List<TrainingDTO>> getTrainings() {
        return ResponseEntity.ok(trainingMapper.getTrainings());
    }

    @GetMapping("{uuid}")
    public ResponseEntity<TrainingDTO> getTraining(@PathVariable String uuid) {
        return ResponseEntity.ok(trainingMapper.getTraining(uuid));
    }

    @PostMapping
    public ResponseEntity<Void> addTraining(@RequestBody TrainingDTO trainingDTO) {
        trainingMapper.addTraining(trainingDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("{uuid}")
    public ResponseEntity<Void> updateTraining(@PathVariable String uuid, @RequestBody TrainingDTO trainingDTO) {
        trainingMapper.updateTraining(uuid, trainingDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{uuid}")
    public ResponseEntity<Void> deleteTraining(@PathVariable String uuid) {
        trainingMapper.deleteTraining(uuid);
        return ResponseEntity.noContent().build();
    }
}
