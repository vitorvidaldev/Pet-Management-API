package dev.vitorvidal.petmanagementapi.domain.model;

import java.util.UUID;

public record JwtResponse(String token, UUID userId) {
}
