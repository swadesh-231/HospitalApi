package com.hospitalapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospitalapi.dto.LoginRequest;
import com.hospitalapi.dto.SignUpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Register - Success")
    void register_WithValidRequest_ReturnsSuccess() throws Exception {
        // Use unique username to avoid conflicts
        String uniqueUsername = "testuser_" + UUID.randomUUID().toString().substring(0, 8);
        SignUpRequest request = new SignUpRequest(uniqueUsername, "password123");

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.username").value(uniqueUsername));
    }

    @Test
    @DisplayName("Register - Duplicate User")
    void register_WithExistingUsername_ThrowsException() throws Exception {
        String username = "duplicate_" + UUID.randomUUID().toString().substring(0, 8);
        SignUpRequest request = new SignUpRequest(username, "password123");

        // First registration should succeed
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        // Second registration with same username should fail
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("Login - Success")
    void login_WithValidCredentials_ReturnsJwt() throws Exception {
        // First register a user
        String username = "logintest_" + UUID.randomUUID().toString().substring(0, 8);
        SignUpRequest signupRequest = new SignUpRequest(username, "password123");

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequest)))
                .andExpect(status().isOk());

        // Then login
        LoginRequest loginRequest = new LoginRequest(username, "password123");

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwt").exists())
                .andExpect(jsonPath("$.userId").exists());
    }

    @Test
    @DisplayName("Login - Invalid Credentials")
    void login_WithInvalidCredentials_ThrowsException() throws Exception {
        LoginRequest request = new LoginRequest("nonexistent_user", "wrongpassword");

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is5xxServerError());
    }
}
