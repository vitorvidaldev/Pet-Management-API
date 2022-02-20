package dev.vitorvidal.petmanagementapi.application.controller;

import dev.vitorvidal.petmanagementapi.application.service.UserService;
import dev.vitorvidal.petmanagementapi.model.dto.CreateUserDTO;
import dev.vitorvidal.petmanagementapi.model.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserControllerTest {

    private final UUID userIdMock = UUID.randomUUID();
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;

    @Test
    void shouldGetUserByIdCorrectly() {
        UserDTO userDTOMock = mock(UserDTO.class);
        // when
        when(userService.getUserById(userIdMock)).thenReturn(userDTOMock);
        // then
        ResponseEntity<UserDTO> response = userController.getUserById(userIdMock);
        // assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void shouldSignupCorrectly() {
        UserDTO userDTOMock = mock(UserDTO.class);
        CreateUserDTO createUserDTOMock = mock(CreateUserDTO.class);

        when(userService.signup(createUserDTOMock)).thenReturn(userDTOMock);

        ResponseEntity<UserDTO> response = userController.signup(createUserDTOMock);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void shouldLoginCorrectly() {
        String tokenMock = "token mock";
        CreateUserDTO createUserDTOMock = mock(CreateUserDTO.class);

        when(userService.login(createUserDTOMock)).thenReturn(tokenMock);

        ResponseEntity<String> response = userController.login(createUserDTOMock);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(tokenMock, response.getBody());
    }

    @Test
    void shouldDeleteUserCorrectly() {
        doNothing().when(userService).deleteUser(userIdMock);
        ResponseEntity<Void> response = userController.deleteUser(userIdMock);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}