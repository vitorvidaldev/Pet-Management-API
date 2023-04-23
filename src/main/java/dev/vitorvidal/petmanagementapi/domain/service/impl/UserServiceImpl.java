package dev.vitorvidal.petmanagementapi.domain.service.impl;

import dev.vitorvidal.petmanagementapi.domain.model.JwtResponse;
import dev.vitorvidal.petmanagementapi.domain.model.Login;
import dev.vitorvidal.petmanagementapi.domain.model.Signup;
import dev.vitorvidal.petmanagementapi.domain.model.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl {

    public User getUserById(UUID userId) {
        return null;
    }

    public User signup(Signup signup) {
        return null;
    }

    public JwtResponse login(Login login) {
        return null;
    }

    public void deleteUser(UUID userId) {
    }
}
