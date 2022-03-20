package dev.vitorvidal.petmanagementapi.application.controller;

import dev.vitorvidal.petmanagementapi.application.service.UserService;
import dev.vitorvidal.petmanagementapi.model.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/rest/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get user data")
    @ApiResponse(responseCode = "200", description = "Returns user data",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDTO.class))})
    @ApiResponse(responseCode = "404", description = "User not found",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDTO.class))})
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(
            @PathVariable(value = "userId") UUID userId) {
        UserDTO userDTO = userService.getUserById(userId);
        return ResponseEntity.ok().body(userDTO);
    }

    @Operation(summary = "Signup user")
    @ApiResponse(responseCode = "201", description = "Registered new user",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDTO.class))})
    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(
            @RequestBody @Valid SignupDTO signupDTO) {
        UserDTO userDTO = userService.signup(signupDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @Operation(summary = "Login user")
    @ApiResponse(responseCode = "201", description = "User logged in. Returns JWT access token",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = JwtResponseDTO.class))})
    @ApiResponse(responseCode = "403", description = "Invalid credentials",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDTO.class))})
    @ApiResponse(responseCode = "404", description = "User not found",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDTO.class))})
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(
            @RequestBody @Valid LoginDTO loginDTO) {
        JwtResponseDTO loginData = userService.login(loginDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(loginData);
    }

    @Operation(summary = "Deletes user if exists")
    @ApiResponse(responseCode = "204", description = "User deleted if exists",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema())})
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable(value = "userId") UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
