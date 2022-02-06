package dev.vitorvidal.petmanagementapi.application.controller;

import dev.vitorvidal.petmanagementapi.application.service.UserService;
import dev.vitorvidal.petmanagementapi.model.user.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;

    @Test
    void shouldListAllUsersCorrectly() {
        UserDTO userDTOMock = new UserDTO(
                UUID.fromString("acd1bdb6-c49c-464a-a2f2-ae2bd0c56b49"),
                "test@test.com",
                true,
                LocalDateTime.now()
        );

        // when
        when(userService.listAllUsers()).thenReturn(List.of(userDTOMock));
        // then
        var resp = userController.listAllUsers();
        // assert
        assertNotNull(resp);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertNotNull(resp.getBody());
        assertEquals(List.class, resp.getBody().getClass());
        assertEquals(1, resp.getBody().size());
    }

    @Test
    void getUserById() {
    }

    @Test
    void signup() {
    }

    @Test
    void login() {
    }

    @Test
    void deleteUser() {
    }
}