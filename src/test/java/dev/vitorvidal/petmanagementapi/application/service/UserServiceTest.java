package dev.vitorvidal.petmanagementapi.application.service;

import dev.vitorvidal.petmanagementapi.infrastrucutre.util.JwtTokenUtil;
import dev.vitorvidal.petmanagementapi.model.dto.JwtResponseDTO;
import dev.vitorvidal.petmanagementapi.model.dto.LoginDTO;
import dev.vitorvidal.petmanagementapi.model.dto.SignupDTO;
import dev.vitorvidal.petmanagementapi.model.dto.UserDTO;
import dev.vitorvidal.petmanagementapi.model.entity.UserEntity;
import dev.vitorvidal.petmanagementapi.model.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtTokenUtil jwtTokenUtil;

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
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("User not found", exception.getReason());
        verify(userRepository, times(1)).findByUserId(userIdMock);
    }

    @Test
    void shouldSignupCorrectly() {
        SignupDTO signupDTOMock = mock(SignupDTO.class);
        UserEntity userEntityMock = mock(UserEntity.class);

        UUID userIdMock = UUID.randomUUID();
        String usernameMock = "Test username";
        String emailMock = "test@gmail.com";
        String passwordMock = "secretPassword";
        boolean isActiveMock = true;
        LocalDateTime creationDateMock = LocalDateTime.now();

        when(signupDTOMock.username()).thenReturn(usernameMock);
        when(signupDTOMock.email()).thenReturn(emailMock);
        when(signupDTOMock.password()).thenReturn(passwordMock);

        when(userEntityMock.getUserId()).thenReturn(userIdMock);
        when(userEntityMock.getEmail()).thenReturn(emailMock);
        when(userEntityMock.getIsActive()).thenReturn(isActiveMock);
        when(userEntityMock.getCreationDate()).thenReturn(creationDateMock);

        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntityMock);

        UserDTO signup = userService.signup(signupDTOMock);

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

    @Test
    void shouldLoginCorrectly() {
        LoginDTO loginDTOMock = mock(LoginDTO.class);
        UserEntity userEntityMock = mock(UserEntity.class);
        String emailMock = "test@gmail.com";
        String passwordMock = "Test123...";
        String jwtTokenMock = "asdflkjasdlkfj";

        when(loginDTOMock.email()).thenReturn(emailMock);
        when(loginDTOMock.password()).thenReturn(passwordMock);
        when(userEntityMock.getEmail()).thenReturn(emailMock);
        when(userEntityMock.getPassword()).thenReturn(passwordMock);
        when(userRepository.findById(emailMock)).thenReturn(Optional.of(userEntityMock));

        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTOMock.email(),
                        loginDTOMock.password()
                )
        )).thenReturn(null);
        when(jwtTokenUtil.generateToken(any(UserDetails.class))).thenReturn(jwtTokenMock);

        JwtResponseDTO loginData = userService.login(loginDTOMock);

        assertNotNull(loginData);
        assertEquals(jwtTokenMock, loginData.token());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(2)).findById(emailMock);
        verify(jwtTokenUtil).generateToken(any(UserDetails.class));
    }

    @Test
    void shouldThrowNotFoundExceptionLoggingIn() {
        LoginDTO loginDTOMock = mock(LoginDTO.class);
        String emailMock = "test@gmail.com";
        String passwordMock = "Test123...";

        when(loginDTOMock.email()).thenReturn(emailMock);
        when(loginDTOMock.password()).thenReturn(passwordMock);
        when(userRepository.findById(emailMock)).thenReturn(Optional.empty());

        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTOMock.email(),
                        loginDTOMock.password()
                )
        )).thenReturn(null);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> userService.login(loginDTOMock));

        assertNotNull(exception);
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("User not found", exception.getReason());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findById(emailMock);
        verify(jwtTokenUtil, times(0)).generateToken(any(UserDetails.class));
    }

    @Test
    void shouldThrowForbiddenExceptionWhenUserIsDisabled() {
        LoginDTO loginDTOMock = mock(LoginDTO.class);
        String emailMock = "test@gmail.com";
        String passwordMock = "Test123...";

        when(loginDTOMock.email()).thenReturn(emailMock);
        when(loginDTOMock.password()).thenReturn(passwordMock);

        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTOMock.email(),
                        loginDTOMock.password()
                )
        )).thenThrow(DisabledException.class);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> userService.login(loginDTOMock));

        assertNotNull(exception);
        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
        assertEquals("User disabled", exception.getReason());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(0)).findById(emailMock);
        verify(jwtTokenUtil, times(0)).generateToken(any(UserDetails.class));
    }

    @Test
    void shouldThrowForbiddenExceptionWhenLoggingWithInvalidCredentials() {
        LoginDTO loginDTOMock = mock(LoginDTO.class);
        String emailMock = "test@gmail.com";
        String passwordMock = "Test123...";

        when(loginDTOMock.email()).thenReturn(emailMock);
        when(loginDTOMock.password()).thenReturn(passwordMock);

        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTOMock.email(),
                        loginDTOMock.password()
                )
        )).thenThrow(BadCredentialsException.class);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> userService.login(loginDTOMock));

        assertNotNull(exception);
        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
        assertEquals("Invalid credentials", exception.getReason());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(0)).findById(emailMock);
        verify(jwtTokenUtil, times(0)).generateToken(any(UserDetails.class));
    }
}