package dev.vitorvidal.petmanagementapi.model.pet;

import java.time.LocalDateTime;
import java.util.UUID;

public record PetDTO(
        UUID petId,
        String petName,
        LocalDateTime birthDate,
        String species,
        String breed,
        LocalDateTime creationDate,
        UUID userId
) {
}
