package dev.vitorvidal.petmanagementapi.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record Note(UUID id, String noteType, String noteTitle, String noteDescription, LocalDateTime creationDate,
                   UUID petId) {
}
