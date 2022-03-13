package dev.vitorvidal.petmanagementapi.model.dto;

import java.util.UUID;

public record JwtResponseDTO(String token, UUID userId) {
}
