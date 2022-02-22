package dev.vitorvidal.petmanagementapi.application.controller;

import dev.vitorvidal.petmanagementapi.application.service.UserService;
import dev.vitorvidal.petmanagementapi.model.dto.CreateUserDTO;
import dev.vitorvidal.petmanagementapi.model.dto.UserDTO;
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

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(
            @PathVariable(value = "id") UUID id) {
        UserDTO userDTO = userService.getUserById(id);
        return ResponseEntity.ok().body(userDTO);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(
            @RequestBody @Valid CreateUserDTO createUserDTO) {
        UserDTO userDTO = userService.signup(createUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody @Valid CreateUserDTO createUserDTO) {
        String token = userService.login(createUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(token);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable(value = "id") UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
