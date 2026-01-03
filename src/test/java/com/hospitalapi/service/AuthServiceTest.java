package com.hospitalapi.service;

import com.hospitalapi.dto.LoginRequest;
import com.hospitalapi.dto.LoginResponse;
import com.hospitalapi.dto.SignUpRequest;
import com.hospitalapi.dto.SignupResponse;
import com.hospitalapi.entity.User;
import com.hospitalapi.entity.enums.AuthProvider;
import com.hospitalapi.entity.enums.RoleType;
import com.hospitalapi.repository.UserRepository;
import com.hospitalapi.security.jwt.JwtService;
import com.hospitalapi.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .username("testuser")
                .email("test@email.com")
                .password("encodedPassword")
                .authProvider(AuthProvider.EMAIL)
                .roles(Set.of(RoleType.PATIENT))
                .build();
    }

    // ==================== SIGNUP TESTS ====================

    @Test
    @DisplayName("Signup - Success")
    void signup_WithValidRequest_ReturnsSignupResponse() {
        SignUpRequest request = new SignUpRequest("newuser", "password123");

        when(userRepository.findByUsername("newuser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        SignupResponse response = authService.signup(request);

        assertNotNull(response);
        assertEquals(1L, response.id());
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Signup - User Already Exists")
    void signup_WithExistingUsername_ThrowsException() {
        SignUpRequest request = new SignUpRequest("existinguser", "password123");

        when(userRepository.findByUsername("existinguser")).thenReturn(Optional.of(testUser));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authService.signup(request));

        assertEquals("User already exists", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Signup - Assigns PATIENT Role by Default")
    void signup_AssignsPatientRole() {
        SignUpRequest request = new SignUpRequest("newuser", "password123");

        when(userRepository.findByUsername("newuser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            assertTrue(savedUser.getRoles().contains(RoleType.PATIENT));
            savedUser.setId(1L);
            return savedUser;
        });

        authService.signup(request);

        verify(userRepository).save(any(User.class));
    }

    // ==================== LOGIN TESTS ====================

    @Test
    @DisplayName("Login - Success")
    void login_WithValidCredentials_ReturnsLoginResponse() {
        LoginRequest request = new LoginRequest("testuser", "password123");
        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(testUser);
        when(jwtService.generateToken(testUser)).thenReturn("jwt.token.here");

        LoginResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("jwt.token.here", response.jwt());
        assertEquals(1L, response.userId());
    }

    @Test
    @DisplayName("Login - Invalid Credentials")
    void login_WithInvalidCredentials_ThrowsException() {
        LoginRequest request = new LoginRequest("testuser", "wrongpassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        assertThrows(BadCredentialsException.class, () -> authService.login(request));
    }

    @Test
    @DisplayName("Login - Generates JWT Token")
    void login_GeneratesJwtToken() {
        LoginRequest request = new LoginRequest("testuser", "password123");
        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(testUser);
        when(jwtService.generateToken(testUser)).thenReturn("generated.jwt.token");

        LoginResponse response = authService.login(request);

        verify(jwtService).generateToken(testUser);
        assertEquals("generated.jwt.token", response.jwt());
    }
}
