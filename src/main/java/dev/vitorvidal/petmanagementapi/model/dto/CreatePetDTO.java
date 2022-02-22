package dev.vitorvidal.petmanagementapi.model.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record CreatePetDTO(
        @NotNull
        String name,
        @NotNull
        LocalDateTime birthDate,
        @NotNull
        String species,
        @NotNull
        String breed
) {
}