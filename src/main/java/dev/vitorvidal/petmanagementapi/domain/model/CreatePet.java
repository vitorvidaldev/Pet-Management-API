package dev.vitorvidal.petmanagementapi.domain.model;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreatePet(@NotNull String name, @NotNull LocalDateTime birthDate, @NotNull String species,
                        @NotNull String breed) {
}
