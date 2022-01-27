package dev.vitorvidal.petmanagementapi.model.note;

import java.time.LocalDateTime;
import java.util.UUID;

public record NoteDTO(
        UUID id,
        String noteType,
        String noteTitle,
        String noteDescription,
        LocalDateTime creationDate,
        UUID petId
) {
}
