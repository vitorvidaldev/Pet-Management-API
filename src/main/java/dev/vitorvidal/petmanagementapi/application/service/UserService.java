package dev.vitorvidal.petmanagementapi.application.service;

import dev.vitorvidal.petmanagementapi.model.dto.CreateUserDTO;
import dev.vitorvidal.petmanagementapi.model.dto.UserDTO;
import dev.vitorvidal.petmanagementapi.model.entity.UserEntity;
import dev.vitorvidal.petmanagementapi.model.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO getUserById(UUID userId) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUserId(userId);
        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            return new UserDTO(
                    userEntity.getUserId(),
                    userEntity.getEmail(),
                    userEntity.getIsActive(),
                    userEntity.getCreationDate());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }

    public UserDTO signup(CreateUserDTO createUserDTO) {
        UserEntity userEntity = userRepository.save(new UserEntity(
                createUserDTO.username(),
                createUserDTO.email(),
                createUserDTO.password()
        ));
        return new UserDTO(
                userEntity.getUserId(),
                userEntity.getEmail(),
                userEntity.getIsActive(),
                userEntity.getCreationDate());
    }

    public String login(CreateUserDTO createUserDTO) {
        Optional<UserEntity> optionalUser = userRepository.findById(createUserDTO.email());

        if (optionalUser.isPresent()) {
            Boolean isValid = optionalUser.get().validatePassword(createUserDTO.password());
            if (isValid) {
                // TODO generate jwt access token
                return "Top";
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }

    public void deleteUser(UUID userId) {
        Optional<UserEntity> optionalUser = userRepository.findByUserId(userId);
        optionalUser.ifPresent(userEntity -> userRepository.deleteById(userEntity.getEmail()));
    }
}
