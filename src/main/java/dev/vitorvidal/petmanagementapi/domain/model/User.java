package dev.vitorvidal.petmanagementapi.domain.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record User(@NotNull UUID id, @Email @NotNull String email, @NotNull Boolean isActive,
                   @NotNull LocalDateTime creationDate) {
}
