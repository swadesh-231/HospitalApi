package com.hospitalapi.service;

import com.hospitalapi.dto.LoginRequest;
import com.hospitalapi.dto.LoginResponse;
import com.hospitalapi.dto.SignUpRequest;
import com.hospitalapi.dto.SignupResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);

    SignupResponse signup(SignUpRequest signupRequest);
    ResponseEntity<LoginResponse> handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId);
}
