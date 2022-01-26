package dev.vitorvidal.petmanagementapi.model.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public record UserDTO(
        @NotNull
        String id,
        @Email
        @NotNull
        String email,
        @NotNull
        Boolean isActive,
        @NotNull
        String creationDate
) {
}
