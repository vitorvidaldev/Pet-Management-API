package dev.vitorvidal.petmanagementapi.application.controller;

import dev.vitorvidal.petmanagementapi.application.service.PetService;
import dev.vitorvidal.petmanagementapi.model.dto.CreatePetDTO;
import dev.vitorvidal.petmanagementapi.model.dto.PetDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/v1/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/user/{userId}/pet/{petId}")
    public ResponseEntity<PetDTO> getPetById(@PathVariable(value = "userId") UUID userId,
                                             @PathVariable(value = "petId") UUID petId) {
        PetDTO petDTO = petService.getPetById(userId, petId);
        return ResponseEntity.ok().body(petDTO);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PetDTO>> getPetByUser(
            @PathVariable(value = "userId") UUID userId,
            @RequestParam(value = "size", defaultValue = "10", required = false) int pageSize) {
        List<PetDTO> petDTO = petService.getPetByUser(userId, pageSize);
        return ResponseEntity.ok().body(petDTO);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<PetDTO> createPet(@RequestBody @Valid CreatePetDTO createPetDTO,
                                            @PathVariable(value = "userId") UUID userId) {
        PetDTO petDTO = petService.createPet(createPetDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(petDTO);
    }

    @DeleteMapping("/user/{userId}/pet/{petId}")
    public ResponseEntity<Void> deletePet(@PathVariable(value = "userId") UUID userId,
                                          @PathVariable(value = "petId") UUID petId) {
        petService.deletePet(userId, petId);
        return ResponseEntity.noContent().build();
    }
}
