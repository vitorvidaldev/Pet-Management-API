package dev.vitorvidal.petmanagementapi.domain.model;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record Signup(@NotNull String username, @Email @NotNull String email, @NotNull String password) {
}
