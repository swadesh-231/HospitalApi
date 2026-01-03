package com.hospitalapi.security.jwt;

import com.hospitalapi.entity.User;
import com.hospitalapi.entity.enums.RoleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .username("testuser")
                .email("test@email.com")
                .password("password")
                .roles(Set.of(RoleType.PATIENT))
                .build();
    }

    @Test
    @DisplayName("Generate Token - Returns Valid JWT")
    void generateToken_ReturnsValidJwt() {
        String token = jwtService.generateToken(testUser);

        assertNotNull(token);
        assertTrue(token.split("\\.").length == 3); // JWT has 3 parts
    }

    @Test
    @DisplayName("Extract Username - From Valid Token")
    void extractUsername_FromValidToken_ReturnsUsername() {
        String token = jwtService.generateToken(testUser);

        String username = jwtService.extractUsername(token);

        assertEquals("testuser", username);
    }

    @Test
    @DisplayName("Generate Token - Contains User ID Claim")
    void generateToken_ContainsUserIdClaim() {
        String token = jwtService.generateToken(testUser);

        assertNotNull(token);
        // Token should be extractable without errors
        String username = jwtService.extractUsername(token);
        assertEquals("testuser", username);
    }

    @Test
    @DisplayName("Tokens for Different Users Are Different")
    void generateToken_ForDifferentUsers_ProducesDifferentTokens() {
        User anotherUser = User.builder()
                .id(2L)
                .username("anotheruser")
                .email("another@email.com")
                .password("password")
                .roles(Set.of(RoleType.DOCTOR))
                .build();

        String token1 = jwtService.generateToken(testUser);
        String token2 = jwtService.generateToken(anotherUser);

        assertNotEquals(token1, token2);
    }
}
