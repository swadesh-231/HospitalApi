package com.hospitalapi.service.impl;

import com.hospitalapi.dto.LoginRequest;
import com.hospitalapi.dto.LoginResponse;
import com.hospitalapi.dto.SignUpRequest;
import com.hospitalapi.dto.SignupResponse;
import com.hospitalapi.entity.User;
import com.hospitalapi.entity.enums.AuthProvider;
import com.hospitalapi.entity.enums.RoleType;
import com.hospitalapi.repository.UserRepository;
import com.hospitalapi.security.jwt.JwtService;
import com.hospitalapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
        private final AuthenticationManager authenticationManager;
        private final JwtService jwtService;
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;

        @Override
        public LoginResponse login(LoginRequest loginRequest) {
                Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(loginRequest.username(),
                                                loginRequest.password()));
                User user = (User) authentication.getPrincipal();
                String token = jwtService.generateToken(user);
                return new LoginResponse(token, user.getId());
        }

        @Override
        public SignupResponse signup(SignUpRequest signupRequest) {
                if (userRepository.findByUsername(signupRequest.username()).isPresent()) {
                        throw new RuntimeException("User already exists");
                }

                User newUser = userRepository.save(
                                User.builder()
                                                .username(signupRequest.username())
                                                .password(passwordEncoder.encode(signupRequest.password()))
                                                .authProvider(AuthProvider.EMAIL)
                                                .roles(Set.of(RoleType.PATIENT))
                                                .build());
                return new SignupResponse(newUser.getId(), newUser.getUsername());
        }

        @Override
        public ResponseEntity<LoginResponse> handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId) {
                String email = oAuth2User.getAttribute("email");

                log.info("OAuth2 login attempt - provider: {}, email: {}", registrationId, email);

                AuthProvider provider = getAuthProvider(registrationId);

                User user = userRepository.findByEmail(email)
                                .orElseGet(() -> {
                                        log.info("Creating new OAuth2 user: {}", email);
                                        return userRepository.save(
                                                        User.builder()
                                                                        .username(email)
                                                                        .email(email)
                                                                        .password("")
                                                                        .authProvider(provider)
                                                                        .roles(Set.of(RoleType.PATIENT))
                                                                        .build());
                                });

                String token = jwtService.generateToken(user);
                log.info("OAuth2 login successful for user: {}", user.getId());

                return ResponseEntity.ok(new LoginResponse(token, user.getId()));
        }

        private AuthProvider getAuthProvider(String registrationId) {
                return switch (registrationId.toLowerCase()) {
                        case "google" -> AuthProvider.GOOGLE;
                        default -> AuthProvider.EMAIL;
                };
        }
}
