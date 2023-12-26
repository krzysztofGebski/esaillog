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

    private final TrainingService trainingService;

    /**
     * Retrieves all trainings.
     *
     * @return a {@link ResponseEntity} containing a list of TrainingDTO objects.
     */
    @GetMapping
    public ResponseEntity<List<TrainingDTO>> retrieveAllTrainings() {
        return ResponseEntity.ok(trainingService.getTrainings());
    }

    /**
     * Retrieves a training with the specific UUID.
     *
     * @param uuid the UUID of the training.
     * @return a {@link ResponseEntity} containing the TrainingDTO
     */
    @GetMapping("{uuid}")
    public ResponseEntity<TrainingDTO> retrieveTrainingByUUID(@PathVariable String uuid) {
        return ResponseEntity.ok(trainingService.getTraining(uuid));
    }

    /**
     * Adds a training to the system.
     *
     * @param trainingDTO the TrainingDTO object representing the training to be added.
     *
     * @return a {@link ResponseEntity} with no content if the training was successfully added.
     */
    @PostMapping
    public ResponseEntity<Void> addTraining(@RequestBody TrainingDTO trainingDTO) {
        trainingService.addTraining(trainingDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * Updates a training with the specified UUID.
     *
     * @param uuid         the UUID of the training to update.
     * @param trainingDTO  the updated TrainingDTO object.
     * @return a {@link ResponseEntity} with no content if the update was successful.
     */
    @PostMapping("{uuid}")
    public ResponseEntity<Void> updateTraining(@PathVariable String uuid, @RequestBody TrainingDTO trainingDTO) {
        trainingService.updateTraining(uuid, trainingDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * Deletes a training from the system.
     *
     * @param uuid the UUID of the training to delete.
     * @return a {@link ResponseEntity} with no content if the deletion was successful.
     */
    @DeleteMapping("{uuid}")
    public ResponseEntity<Void> deleteTraining(@PathVariable String uuid) {
        trainingService.deleteTraining(uuid);
        return ResponseEntity.noContent().build();
    }
}
