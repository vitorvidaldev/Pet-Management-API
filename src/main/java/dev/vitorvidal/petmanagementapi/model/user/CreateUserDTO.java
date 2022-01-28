package dev.vitorvidal.petmanagementapi.model.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public record CreateUserDTO(
        @NotNull
        String username,
        @Email
        @NotNull
        String email,
        @NotNull
        String password
) {
}
