package dev.vitorvidal.petmanagementapi.application.service;

import dev.vitorvidal.petmanagementapi.application.repository.UserRepository;
import dev.vitorvidal.petmanagementapi.model.user.CreateUserDTO;
import dev.vitorvidal.petmanagementapi.model.user.UserDTO;
import dev.vitorvidal.petmanagementapi.model.user.UserEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                    userEntity.getId(),
                    userEntity.getEmail(),
                    userEntity.getActive(),
                    userEntity.getCreationDate()
            ));
        }
        return userDtoList;
    }

    public UserDTO getUserById(String id) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();

            return new UserDTO(
                    userEntity.getId(),
                    userEntity.getEmail(),
                    userEntity.getActive(),
                    userEntity.getCreationDate()
            );
        }
        return null;
    }

    public UserDTO signup(CreateUserDTO createUserDTO) {
        UserEntity userEntity = userRepository.save(new UserEntity(
                createUserDTO.username(),
                createUserDTO.email(),
                createUserDTO.password()
        ));

        return new UserDTO(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getActive(),
                userEntity.getCreationDate()
        );
    }

    public String login(CreateUserDTO createUserDTO) {
        // TODO validate password
        // TODO generate jwt access token

        return null;
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
