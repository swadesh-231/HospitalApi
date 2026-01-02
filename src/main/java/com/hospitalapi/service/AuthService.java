package com.hospitalapi.service;

import com.hospitalapi.dto.LoginRequest;
import com.hospitalapi.dto.LoginResponse;
import com.hospitalapi.dto.SignUpRequest;
import com.hospitalapi.dto.SignupResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
    SignupResponse signup(SignUpRequest signupRequest);
}
