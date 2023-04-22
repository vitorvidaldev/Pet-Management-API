package dev.vitorvidal.petmanagementapi.application.controller;

import dev.vitorvidal.petmanagementapi.controller.UserController;
import dev.vitorvidal.petmanagementapi.domain.model.JwtResponse;
import dev.vitorvidal.petmanagementapi.domain.model.Login;
import dev.vitorvidal.petmanagementapi.domain.model.Signup;
import dev.vitorvidal.petmanagementapi.domain.model.User;
import dev.vitorvidal.petmanagementapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
        User userMock = mock(User.class);
        // when
        when(userService.getUserById(userIdMock)).thenReturn(userMock);
        // then
        ResponseEntity<User> response = userController.getUserById(userIdMock);
        // assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void shouldSignupCorrectly() {
        User userMock = mock(User.class);
        Signup signupMock = mock(Signup.class);

        when(userService.signup(signupMock)).thenReturn(userMock);

        ResponseEntity<User> response = userController.signup(signupMock);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void shouldLoginCorrectly() {
        String tokenMock = "token mock";
        Login loginMock = mock(Login.class);
        UUID userIdMock = UUID.randomUUID();

        when(userService.login(loginMock)).thenReturn(new JwtResponse(tokenMock, userIdMock));

        ResponseEntity<JwtResponse> response = userController.login(loginMock);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(tokenMock, response.getBody().token());
        assertEquals(userIdMock, response.getBody().userId());
    }

    @Test
    void shouldDeleteUserCorrectly() {
        doNothing().when(userService).deleteUser(userIdMock);
        ResponseEntity<Void> response = userController.deleteUser(userIdMock);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}