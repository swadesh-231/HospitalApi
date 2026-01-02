package com.hospitalapi.service.impl;


import com.hospitalapi.dto.LoginRequest;
import com.hospitalapi.dto.LoginResponse;
import com.hospitalapi.dto.SignUpRequest;
import com.hospitalapi.dto.SignupResponse;
import com.hospitalapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public SignupResponse signup(SignUpRequest signupRequest) {
        return null;
    }
}
