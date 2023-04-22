package dev.vitorvidal.petmanagementapi.controller;

import dev.vitorvidal.petmanagementapi.domain.model.JwtResponse;
import dev.vitorvidal.petmanagementapi.domain.model.Login;
import dev.vitorvidal.petmanagementapi.domain.model.Signup;
import dev.vitorvidal.petmanagementapi.domain.model.User;
import dev.vitorvidal.petmanagementapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/rest/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "userId") UUID userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody @Valid Signup signup) {
        User user = userService.signup(signup);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody @Valid Login login) {
        JwtResponse loginData = userService.login(login);
        return ResponseEntity.status(HttpStatus.CREATED).body(loginData);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable(value = "userId") UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
