package dev.vitorvidal.petmanagementapi.controller;

import dev.vitorvidal.petmanagementapi.domain.model.CreatePet;
import dev.vitorvidal.petmanagementapi.domain.model.Pet;
import dev.vitorvidal.petmanagementapi.service.PetService;
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
    public ResponseEntity<Pet> getPetById(@PathVariable(value = "userId") UUID userId,
                                          @PathVariable(value = "petId") UUID petId) {
        Pet pet = petService.getPetById(userId, petId);
        return ResponseEntity.ok().body(pet);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Pet>> getPetByUser(
            @PathVariable(value = "userId") UUID userId,
            @RequestParam(value = "size", defaultValue = "10", required = false) int pageSize) {
        List<Pet> pet = petService.getPetByUser(userId, pageSize);
        return ResponseEntity.ok().body(pet);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<Pet> createPet(@RequestBody @Valid CreatePet createPet,
                                         @PathVariable(value = "userId") UUID userId) {
        Pet pet = petService.createPet(createPet, userId);
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(pet);
    }

    @DeleteMapping("/user/{userId}/pet/{petId}")
    public ResponseEntity<Void> deletePet(@PathVariable(value = "userId") UUID userId,
                                          @PathVariable(value = "petId") UUID petId) {
        petService.deletePet(userId, petId);
        return ResponseEntity.noContent().build();
    }
}
