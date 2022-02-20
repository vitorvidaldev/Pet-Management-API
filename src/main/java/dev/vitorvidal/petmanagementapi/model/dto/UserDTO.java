package dev.vitorvidal.petmanagementapi.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public record UserDTO(
        @NotNull
        UUID id,
        @Email
        @NotNull
        String email,
        @NotNull
        Boolean isActive,
        @NotNull
        LocalDateTime creationDate
) {
}
