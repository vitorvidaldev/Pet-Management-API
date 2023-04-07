package dev.vitorvidal.petmanagementapi.model.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record SignupDTO(@NotNull String username, @Email @NotNull String email, @NotNull String password) {
}
