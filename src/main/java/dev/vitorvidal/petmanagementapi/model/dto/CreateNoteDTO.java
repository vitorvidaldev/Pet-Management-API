package dev.vitorvidal.petmanagementapi.model.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateNoteDTO(@NotNull String noteType, @NotNull String noteTitle, @NotNull String description,
                            @NotNull UUID userId, @NotNull UUID petId) {
}
