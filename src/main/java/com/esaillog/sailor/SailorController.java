package com.esaillog.sailor;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/sailors")
@RequiredArgsConstructor
public class SailorController {

    private final SailorService sailorService;

    /**
     * Retrieves all sailors.
     *
     * @return a {@link ResponseEntity} containing a list of SailorDTO objects.
     */
    @GetMapping
    public ResponseEntity<List<SailorDTO>> retrieveAllSailors() {
        return ResponseEntity.ok(sailorService.getSailors());
    }

    /**
     * Retrieves the sailor with the specified UUID.
     *
     * @param uuid the UUID of the sailor.
     * @return a {@link ResponseEntity} containing the sailor DTO
     */
    @GetMapping("{uuid}")
    public ResponseEntity<SailorDTO> retrieveSailorByUUID(@PathVariable String uuid) {
        return ResponseEntity.ok(sailorService.getSailor(uuid));
    }

    /**
     * Adds a sailor to the system.
     *
     * @param sailorDTO the SailorDTO object representing the sailor to be added.
     *
     * @return a {@link ResponseEntity} with no content if the sailor was successfully added.
     */
    @PostMapping
    public ResponseEntity<Void> addSailor(@RequestBody SailorDTO sailorDTO) {
        sailorService.addSailor(sailorDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * Updates the sailor with the specified UUID.
     *
     * @param uuid      the UUID of the sailor to update
     * @param sailorDTO the updated SailorDTO object
     * @return a {@link ResponseEntity} with no content if the update was successful
     */
    @PostMapping("{uuid}")
    public ResponseEntity<Void> updateSailor(@PathVariable String uuid, @RequestBody SailorDTO sailorDTO) {
        sailorService.updateSailor(uuid, sailorDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * Deletes a sailor from the system.
     *
     * @param uuid the UUID of the sailor to delete
     * @return a {@link ResponseEntity} with no content if the deletion was successful
     */
    @DeleteMapping("{uuid}")
    public ResponseEntity<Void> deleteSailor(@PathVariable String uuid) {
        sailorService.deleteSailor(uuid);
        return ResponseEntity.noContent().build();
    }

}
