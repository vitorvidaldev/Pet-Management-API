package dev.vitorvidal.petmanagementapi.application.service;

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