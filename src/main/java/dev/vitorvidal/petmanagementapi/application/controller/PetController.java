package dev.vitorvidal.petmanagementapi.application.controller;

import dev.vitorvidal.petmanagementapi.application.service.PetService;
import dev.vitorvidal.petmanagementapi.model.dto.CreatePetDTO;
import dev.vitorvidal.petmanagementapi.model.dto.ErrorResponseDTO;
import dev.vitorvidal.petmanagementapi.model.dto.PetDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Get pet data")
    @ApiResponse(responseCode = "200", description = "Returns pet data",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PetDTO.class))})
    @ApiResponse(responseCode = "404", description = "Pet not found",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDTO.class))})
    @GetMapping("/user/{userId}/pet/{petId}")
    public ResponseEntity<PetDTO> getPetById(
            @PathVariable(value = "userId") UUID userId,
            @PathVariable(value = "petId") UUID petId) {
        PetDTO petDTO = petService.getPetById(userId, petId);
        return ResponseEntity.ok().body(petDTO);
    }

    @Operation(summary = "Get users pets data")
    @ApiResponse(responseCode = "200", description = "Returns users pets data",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = List.class))})
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PetDTO>> getPetByUser(
            @PathVariable(value = "userId") UUID userId,
            @RequestParam(value = "size", defaultValue = "10", required = false) int pageSize) {
        List<PetDTO> petDTO = petService.getPetByUser(userId, pageSize);
        return ResponseEntity.ok().body(petDTO);
    }

    @Operation(summary = "Create pet")
    @ApiResponse(responseCode = "201", description = "Returns pet data",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PetDTO.class))})
    @PostMapping("/user/{userId}")
    public ResponseEntity<PetDTO> createPet(
            @RequestBody @Valid CreatePetDTO createPetDTO,
            @PathVariable(value = "userId") UUID userId) {
        PetDTO petDTO = petService.createPet(createPetDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(petDTO);
    }

    @Operation(summary = "Delete pet data")
    @ApiResponse(responseCode = "204", description = "Deleted pet",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema())})
    @ApiResponse(responseCode = "404", description = "Pet not found",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDTO.class))})
    @DeleteMapping("/user/{userId}/pet/{petId}")
    public ResponseEntity<Void> deletePet(
            @PathVariable(value = "userId") UUID userId,
            @PathVariable(value = "petId") UUID petId) {
        petService.deletePet(userId, petId);
        return ResponseEntity.noContent().build();
    }
}
