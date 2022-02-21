package dev.vitorvidal.petmanagementapi.application.service;

import dev.vitorvidal.petmanagementapi.model.dto.CreateUserDTO;
import dev.vitorvidal.petmanagementapi.model.dto.UserDTO;
import dev.vitorvidal.petmanagementapi.model.entity.UserEntity;
import dev.vitorvidal.petmanagementapi.model.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO getUserById(UUID id) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUserId(id);
        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            return modelMapper.map(userEntity, UserDTO.class);
        }
        throw new RuntimeException("Could not find user with provided id");
    }

    public UserDTO signup(CreateUserDTO createUserDTO) {
        UserEntity userEntity = userRepository.save(modelMapper.map(createUserDTO, UserEntity.class));
        return modelMapper.map(userEntity, UserDTO.class);
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
        Optional<UserEntity> optionalUser = userRepository.findByUserId(id);
        optionalUser.ifPresent(userEntity -> userRepository.deleteById(userEntity.getEmail()));
    }
}
