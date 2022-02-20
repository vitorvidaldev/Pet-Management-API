package dev.vitorvidal.petmanagementapi.model.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public record CreateNoteDTO(
        @NotNull
        String noteType,
        @NotNull
        String noteTitle,
        @NotNull
        String description,
        @NotNull
        LocalDateTime creationDate,
        @NotNull
        UUID noteId,
        @NotNull
        UUID petId
) {
}
