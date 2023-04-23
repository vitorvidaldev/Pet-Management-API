package dev.vitorvidal.petmanagementapi.controller;

import dev.vitorvidal.petmanagementapi.domain.model.Signup;
import dev.vitorvidal.petmanagementapi.domain.model.User;
import dev.vitorvidal.petmanagementapi.domain.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/rest/v1/users")
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "userId") UUID userId) {
        User user = userServiceImpl.getUserById(userId);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody @Valid Signup signup) {
        User user = userServiceImpl.signup(signup);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable(value = "userId") UUID userId) {
        userServiceImpl.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
