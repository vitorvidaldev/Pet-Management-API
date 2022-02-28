package dev.vitorvidal.petmanagementapi.application.service;

import dev.vitorvidal.petmanagementapi.infrastrucutre.util.JwtTokenUtil;
import dev.vitorvidal.petmanagementapi.model.dto.CreateUserDTO;
import dev.vitorvidal.petmanagementapi.model.dto.UserDTO;
import dev.vitorvidal.petmanagementapi.model.entity.UserEntity;
import dev.vitorvidal.petmanagementapi.model.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public UserService(
            UserRepository userRepository,
            @Lazy AuthenticationManager authenticationManager,
            JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
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
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        UserEntity userEntity = userRepository.save(new UserEntity(
                createUserDTO.username(),
                createUserDTO.email(),
                passwordEncoder.encode(createUserDTO.password())
        ));
        return new UserDTO(
                userEntity.getUserId(),
                userEntity.getEmail(),
                userEntity.getIsActive(),
                userEntity.getCreationDate());
    }

    public String login(CreateUserDTO createUserDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            createUserDTO.email(),
                            createUserDTO.password()
                    )
            );
        } catch (DisabledException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User disabled", e);
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid credentials", e);
        }

        UserDetails userDetails = loadUserByUsername(createUserDTO.email());
        return jwtTokenUtil.generateToken(userDetails);
    }

    public void deleteUser(UUID userId) {
        Optional<UserEntity> optionalUser = userRepository.findByUserId(userId);
        optionalUser.ifPresent(userEntity -> userRepository.deleteById(userEntity.getEmail()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUser = userRepository.findById(username);
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        UserEntity userEntity = optionalUser.get();

        if (userEntity.getEmail().equals(username)) {
            return new User(
                    userEntity.getEmail(),
                    userEntity.getPassword(),
                    new ArrayList<>()
            );
        } else {
            throw new UsernameNotFoundException("User not found with email: " + userEntity.getEmail());
        }
    }
}
