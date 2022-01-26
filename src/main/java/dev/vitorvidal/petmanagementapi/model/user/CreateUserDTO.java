package dev.vitorvidal.petmanagementapi.model.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public record CreateUserDTO(
        @Email
        @NotNull
        String email,
        @NotNull
        @Pattern(regexp = "/((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$/")
        String password
) {
}
