package dev.vitorvidal.petmanagementapi.model.pet;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public record CreatePetDTO(
        @NotNull
        String name,
        @NotNull
        LocalDateTime birthDate,
        @NotNull
        String species,
        @NotNull
        String breed,
        @NotNull
        UUID userId
) {
}
