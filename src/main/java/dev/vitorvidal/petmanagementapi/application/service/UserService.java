package dev.vitorvidal.petmanagementapi.application.service;

import dev.vitorvidal.petmanagementapi.application.repository.UserRepository;
import dev.vitorvidal.petmanagementapi.model.user.CreateUserDTO;
import dev.vitorvidal.petmanagementapi.model.user.UserDTO;
import dev.vitorvidal.petmanagementapi.model.user.UserEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> listAllUsers() {
        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserDTO> userDtoList = new ArrayList<>();
        for (UserEntity userEntity : userEntityList) {
            userDtoList.add(new UserDTO(
                    userEntity.getUserId(),
                    userEntity.getEmail(),
                    userEntity.getActive(),
                    userEntity.getCreationDate()
            ));
        }
        return userDtoList;
    }

    public UserDTO getUserById(UUID id) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUserId(id);
        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();

            return new UserDTO(
                    userEntity.getUserId(),
                    userEntity.getEmail(),
                    userEntity.getActive(),
                    userEntity.getCreationDate()
            );
        }
        throw new RuntimeException("Could not find user with provided id");
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
                userEntity.getActive(),
                userEntity.getCreationDate()
        );
    }

    public String login(CreateUserDTO createUserDTO) {
        Optional<UserEntity> optionalUser = userRepository.findById(createUserDTO.email());

        if (optionalUser.isPresent()) {
            Boolean isValid = optionalUser.get().validatePassword(createUserDTO.password());
            if (isValid) {
                return "Top";
            }
        }
        // TODO generate jwt access token
        throw new RuntimeException("Could not validate user");
    }

    public void deleteUser(UUID id) {
        userRepository.deleteByUserId(id);
    }
}
