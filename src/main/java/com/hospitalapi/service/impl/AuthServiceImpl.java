package com.hospitalapi.service.impl;

import com.hospitalapi.dto.LoginRequest;
import com.hospitalapi.dto.LoginResponse;
import com.hospitalapi.dto.SignUpRequest;
import com.hospitalapi.dto.SignupResponse;
import com.hospitalapi.entity.User;
import com.hospitalapi.repository.UserRepository;
import com.hospitalapi.security.jwt.JwtService;
import com.hospitalapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
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
                // Check if user already exists
                if (userRepository.findByUsername(signupRequest.username()).isPresent()) {
                        throw new RuntimeException("User already exists");
                }

                User newUser = userRepository.save(
                                User.builder().username(signupRequest.username())
                                        .password(passwordEncoder.encode(signupRequest.password()))
                                        .build());
                return new SignupResponse(newUser.getId(), newUser.getUsername());
        }
}
