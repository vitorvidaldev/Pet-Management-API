package dev.vitorvidal.petmanagementapi.application.service;

import dev.vitorvidal.petmanagementapi.application.repository.UserRepository;
import dev.vitorvidal.petmanagementapi.model.user.CreateUserDTO;
import dev.vitorvidal.petmanagementapi.model.user.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> listAllUsers() {
        return null;
    }

    public UserDTO getUserById(String id) {
        return null;
    }

    public UserDTO signup(CreateUserDTO createUserDTO) {
        return null;
    }

    public String login(CreateUserDTO createUserDTO) {
        return null;
    }

    public void deleteUser(String id) {

    }
}
