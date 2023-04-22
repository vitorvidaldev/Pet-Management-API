package dev.vitorvidal.petmanagementapi.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record Pet(UUID petId, String petName, LocalDateTime birthDate, String species, String breed,
                  LocalDateTime creationDate, UUID userId) {
}
