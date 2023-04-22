package dev.vitorvidal.petmanagementapi.domain.model;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateNote(@NotNull String noteType, @NotNull String noteTitle, @NotNull String description,
                         @NotNull UUID userId, @NotNull UUID petId) {
}
