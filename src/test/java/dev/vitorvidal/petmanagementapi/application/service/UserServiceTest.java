package dev.vitorvidal.petmanagementapi.application.service;

import dev.vitorvidal.petmanagementapi.model.dto.CreateUserDTO;
import dev.vitorvidal.petmanagementapi.model.dto.UserDTO;
import dev.vitorvidal.petmanagementapi.model.entity.UserEntity;
import dev.vitorvidal.petmanagementapi.model.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Test
    void shouldGetUserByIdCorrectly() {
        UUID userIdMock = UUID.randomUUID();
        UserEntity userEntityMock = mock(UserEntity.class);

        when(userRepository.findByUserId(userIdMock)).thenReturn(Optional.of(userEntityMock));

        UserDTO userDTO = userService.getUserById(userIdMock);

        assertNotNull(userDTO);
        verify(userRepository, times(1)).findByUserId(userIdMock);
    }

    @Test
    void shouldThrowNotFoundExceptionGettingUser() {
        UUID userIdMock = UUID.randomUUID();

        when(userRepository.findByUserId(userIdMock)).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> userService.getUserById(userIdMock));

        assertNotNull(exception);
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("User not found", exception.getReason());
        verify(userRepository, times(1)).findByUserId(userIdMock);
    }

    @Test
    void shouldSignupCorrectly() {
        CreateUserDTO createUserDTOMock = mock(CreateUserDTO.class);
        UserEntity userEntityMock = mock(UserEntity.class);

        UUID userIdMock = UUID.randomUUID();
        String usernameMock = "Test username";
        String emailMock = "test@gmail.com";
        String passwordMock = "secretPassword";
        boolean isActiveMock = true;
        LocalDateTime creationDateMock = LocalDateTime.now();

        when(createUserDTOMock.username()).thenReturn(usernameMock);
        when(createUserDTOMock.email()).thenReturn(emailMock);
        when(createUserDTOMock.password()).thenReturn(passwordMock);

        when(userEntityMock.getUserId()).thenReturn(userIdMock);
        when(userEntityMock.getEmail()).thenReturn(emailMock);
        when(userEntityMock.getIsActive()).thenReturn(isActiveMock);
        when(userEntityMock.getCreationDate()).thenReturn(creationDateMock);

        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntityMock);

        UserDTO signup = userService.signup(createUserDTOMock);

        verify(userRepository, times(1)).save(any(UserEntity.class));

        assertNotNull(signup);
        assertEquals(userIdMock, signup.id());
        assertEquals(emailMock, signup.email());
        assertEquals(isActiveMock, signup.isActive());
        assertEquals(creationDateMock, signup.creationDate());
    }

    @Test
    void shouldDeleteUserCorrectly() {
        UUID userIdMock = UUID.randomUUID();
        UserEntity userEntityMock = mock(UserEntity.class);
        String emailMock = "test@gmail.com";

        when(userRepository.findByUserId(userIdMock)).thenReturn(Optional.of(userEntityMock));
        when(userEntityMock.getEmail()).thenReturn(emailMock);
        doNothing().when(userRepository).deleteById(emailMock);

        userService.deleteUser(userIdMock);

        verify(userRepository, times(1)).findByUserId(userIdMock);
        verify(userRepository, times(1)).deleteById(emailMock);
    }
}