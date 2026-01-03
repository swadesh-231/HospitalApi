package com.hospitalapi.repository;

import com.hospitalapi.entity.User;
import com.hospitalapi.entity.enums.AuthProvider;
import com.hospitalapi.entity.enums.RoleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        testUser = User.builder()
                .username("testuser")
                .email("test@email.com")
                .password("password")
                .authProvider(AuthProvider.EMAIL)
                .roles(Set.of(RoleType.PATIENT))
                .build();
    }

    @Test
    @DisplayName("Save User - Success")
    void saveUser_Success() {
        User savedUser = userRepository.save(testUser);

        assertNotNull(savedUser.getId());
        assertEquals("testuser", savedUser.getUsername());
    }

    @Test
    @DisplayName("Find by Username - Found")
    void findByUsername_WhenExists_ReturnsUser() {
        userRepository.save(testUser);

        Optional<User> found = userRepository.findByUsername("testuser");

        assertTrue(found.isPresent());
        assertEquals("testuser", found.get().getUsername());
    }

    @Test
    @DisplayName("Find by Username - Not Found")
    void findByUsername_WhenNotExists_ReturnsEmpty() {
        Optional<User> found = userRepository.findByUsername("nonexistent");

        assertTrue(found.isEmpty());
    }

    @Test
    @DisplayName("Find by Email - Found")
    void findByEmail_WhenExists_ReturnsUser() {
        userRepository.save(testUser);

        Optional<User> found = userRepository.findByEmail("test@email.com");

        assertTrue(found.isPresent());
        assertEquals("test@email.com", found.get().getEmail());
    }

    @Test
    @DisplayName("Find by Email - Not Found")
    void findByEmail_WhenNotExists_ReturnsEmpty() {
        Optional<User> found = userRepository.findByEmail("nonexistent@email.com");

        assertTrue(found.isEmpty());
    }

    @Test
    @DisplayName("User Has Roles")
    void savedUser_HasRoles() {
        User savedUser = userRepository.save(testUser);

        User retrieved = userRepository.findById(savedUser.getId()).orElseThrow();

        assertNotNull(retrieved.getRoles());
        assertTrue(retrieved.getRoles().contains(RoleType.PATIENT));
    }
}
