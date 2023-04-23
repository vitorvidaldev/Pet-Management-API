package dev.vitorvidal.petmanagementapi.service;

import dev.vitorvidal.petmanagementapi.entity.UserEntity;
import dev.vitorvidal.petmanagementapi.domain.model.JwtResponse;
import dev.vitorvidal.petmanagementapi.domain.model.Login;
import dev.vitorvidal.petmanagementapi.domain.model.Signup;
import dev.vitorvidal.petmanagementapi.domain.model.User;
import dev.vitorvidal.petmanagementapi.domain.repository.UserRepository;
import dev.vitorvidal.petmanagementapi.domain.service.impl.UserServiceImpl;
import dev.vitorvidal.petmanagementapi.auth.JwtToken;
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
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userServiceImpl;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtToken jwtToken;

    @Test
    void shouldGetUserByIdCorrectly() {
        UUID userIdMock = UUID.randomUUID();
        UserEntity userEntityMock = mock(UserEntity.class);

        when(userRepository.findByUserId(userIdMock)).thenReturn(Optional.of(userEntityMock));

        User user = userServiceImpl.getUserById(userIdMock);

        assertNotNull(user);
        verify(userRepository, times(1)).findByUserId(userIdMock);
    }

    @Test
    void shouldThrowNotFoundExceptionGettingUser() {
        UUID userIdMock = UUID.randomUUID();

        when(userRepository.findByUserId(userIdMock)).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> userServiceImpl.getUserById(userIdMock));

        assertNotNull(exception);
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("User not found", exception.getReason());
        verify(userRepository, times(1)).findByUserId(userIdMock);
    }

    @Test
    void shouldSignupCorrectly() {
        Signup signupMock = mock(Signup.class);
        UserEntity userEntityMock = mock(UserEntity.class);

        UUID userIdMock = UUID.randomUUID();
        String usernameMock = "Test username";
        String emailMock = "test@gmail.com";
        String passwordMock = "secretPassword";
        boolean isActiveMock = true;
        LocalDateTime creationDateMock = LocalDateTime.now();

        when(signupMock.username()).thenReturn(usernameMock);
        when(signupMock.email()).thenReturn(emailMock);
        when(signupMock.password()).thenReturn(passwordMock);

        when(userEntityMock.getUserId()).thenReturn(userIdMock);
        when(userEntityMock.getEmail()).thenReturn(emailMock);
        when(userEntityMock.getIsActive()).thenReturn(isActiveMock);
        when(userEntityMock.getCreationDate()).thenReturn(creationDateMock);

        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntityMock);

        User signup = userServiceImpl.signup(signupMock);

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

        userServiceImpl.deleteUser(userIdMock);

        verify(userRepository, times(1)).findByUserId(userIdMock);
        verify(userRepository, times(1)).deleteById(emailMock);
    }

    @Test
    void shouldLoginCorrectly() {
        Login loginMock = mock(Login.class);
        UserEntity userEntityMock = mock(UserEntity.class);
        String emailMock = "test@gmail.com";
        String passwordMock = "Test123...";
        String jwtTokenMock = "asdflkjasdlkfj";

        when(loginMock.email()).thenReturn(emailMock);
        when(loginMock.password()).thenReturn(passwordMock);
        when(userEntityMock.getEmail()).thenReturn(emailMock);
        when(userEntityMock.getPassword()).thenReturn(passwordMock);
        when(userRepository.findById(emailMock)).thenReturn(Optional.of(userEntityMock));

        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginMock.email(),
                        loginMock.password()
                )
        )).thenReturn(null);
        when(jwtToken.generateToken(any(UserDetails.class))).thenReturn(jwtTokenMock);

        JwtResponse loginData = userServiceImpl.login(loginMock);

        assertNotNull(loginData);
        assertEquals(jwtTokenMock, loginData.token());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(2)).findById(emailMock);
        verify(jwtToken).generateToken(any(UserDetails.class));
    }

    @Test
    void shouldThrowNotFoundExceptionLoggingIn() {
        Login loginMock = mock(Login.class);
        String emailMock = "test@gmail.com";
        String passwordMock = "Test123...";

        when(loginMock.email()).thenReturn(emailMock);
        when(loginMock.password()).thenReturn(passwordMock);
        when(userRepository.findById(emailMock)).thenReturn(Optional.empty());

        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginMock.email(),
                        loginMock.password()
                )
        )).thenReturn(null);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> userServiceImpl.login(loginMock));

        assertNotNull(exception);
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("User not found", exception.getReason());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findById(emailMock);
        verify(jwtToken, times(0)).generateToken(any(UserDetails.class));
    }

    @Test
    void shouldThrowForbiddenExceptionWhenUserIsDisabled() {
        Login loginMock = mock(Login.class);
        String emailMock = "test@gmail.com";
        String passwordMock = "Test123...";

        when(loginMock.email()).thenReturn(emailMock);
        when(loginMock.password()).thenReturn(passwordMock);

        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginMock.email(),
                        loginMock.password()
                )
        )).thenThrow(DisabledException.class);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> userServiceImpl.login(loginMock));

        assertNotNull(exception);
        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
        assertEquals("User disabled", exception.getReason());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(0)).findById(emailMock);
        verify(jwtToken, times(0)).generateToken(any(UserDetails.class));
    }

    @Test
    void shouldThrowForbiddenExceptionWhenLoggingWithInvalidCredentials() {
        Login loginMock = mock(Login.class);
        String emailMock = "test@gmail.com";
        String passwordMock = "Test123...";

        when(loginMock.email()).thenReturn(emailMock);
        when(loginMock.password()).thenReturn(passwordMock);

        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginMock.email(),
                        loginMock.password()
                )
        )).thenThrow(BadCredentialsException.class);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> userServiceImpl.login(loginMock));

        assertNotNull(exception);
        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
        assertEquals("Invalid credentials", exception.getReason());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(0)).findById(emailMock);
        verify(jwtToken, times(0)).generateToken(any(UserDetails.class));
    }
}