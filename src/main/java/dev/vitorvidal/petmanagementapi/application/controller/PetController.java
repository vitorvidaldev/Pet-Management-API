package dev.vitorvidal.petmanagementapi.application.controller;

import dev.vitorvidal.petmanagementapi.application.service.PetService;
import dev.vitorvidal.petmanagementapi.model.dto.CreatePetDTO;
import dev.vitorvidal.petmanagementapi.model.dto.PetDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/v1/pet")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/{userId}/{petId}")
    public ResponseEntity<PetDTO> getPetById(
            @PathVariable(value = "userId") UUID userId,
            @PathVariable(value = "petId") UUID petId) {
        PetDTO petDTO = petService.getPetById(userId, petId);
        return ResponseEntity.ok().body(petDTO);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<PetDTO>> getPetByUser(@PathVariable(value = "userId") UUID userId) {
        List<PetDTO> petDTO = petService.getPetByUser(userId);
        return ResponseEntity.ok().body(petDTO);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<PetDTO> createPet(
            @RequestBody @Valid CreatePetDTO createPetDTO,
            @PathVariable(value = "userId") UUID userId) {
        PetDTO petDTO = petService.createPet(createPetDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(petDTO);
    }

    @DeleteMapping("/{userId}/{petId}")
    public ResponseEntity<Void> deletePet(
            @PathVariable(value = "userId") UUID userId,
            @PathVariable(value = "petId") UUID petId) {
        petService.deletePet(userId, petId);
        return ResponseEntity.noContent().build();
    }
}
