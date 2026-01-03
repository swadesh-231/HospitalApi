package com.hospitalapi.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    // ==================== PUBLIC ENDPOINTS ====================

    @Test
    @DisplayName("Auth endpoints are public")
    void authEndpoints_ArePublic() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType("application/json")
                .content("{\"username\":\"test\",\"password\":\"pass\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Swagger UI is public")
    void swaggerUi_IsPublic() throws Exception {
        mockMvc.perform(get("/swagger-ui.html"))
                .andExpect(status().is3xxRedirection()); // Redirects to swagger-ui/index.html
    }

    // ==================== PROTECTED ENDPOINTS ====================

    @Test
    @DisplayName("Admin endpoints require authentication")
    void adminEndpoints_RequireAuth_Returns401() throws Exception {
        mockMvc.perform(get("/admin/doctors"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Doctor endpoints require authentication")
    void doctorEndpoints_RequireAuth_Returns401() throws Exception {
        mockMvc.perform(get("/doctors/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Patient endpoints require authentication")
    void patientEndpoints_RequireAuth_Returns401() throws Exception {
        mockMvc.perform(get("/patients/1"))
                .andExpect(status().isUnauthorized());
    }

    // ==================== ROLE-BASED ACCESS ====================

    @Test
    @DisplayName("PATIENT cannot access admin endpoints")
    @WithMockUser(roles = "PATIENT")
    void patientRole_CannotAccessAdmin_Returns403() throws Exception {
        mockMvc.perform(get("/admin/doctors"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("PATIENT cannot access doctor endpoints")
    @WithMockUser(roles = "PATIENT")
    void patientRole_CannotAccessDoctor_Returns403() throws Exception {
        mockMvc.perform(get("/doctors/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("DOCTOR cannot access admin endpoints")
    @WithMockUser(roles = "DOCTOR")
    void doctorRole_CannotAccessAdmin_Returns403() throws Exception {
        mockMvc.perform(get("/admin/doctors"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("ADMIN can access admin endpoints")
    @WithMockUser(roles = "ADMIN")
    void adminRole_CanAccessAdmin_Returns200() throws Exception {
        mockMvc.perform(get("/admin/doctors"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DOCTOR can access doctor endpoints - not forbidden")
    @WithMockUser(roles = "DOCTOR")
    void doctorRole_CanAccessDoctor_NotForbidden() throws Exception {
        // Resource may not exist (404) but security should pass (not 401/403)
        mockMvc.perform(get("/doctors/1"))
                .andExpect(status().isNotFound()); // 404 means security passed, resource not found
    }

    @Test
    @DisplayName("PATIENT can access patient endpoints - not forbidden")
    @WithMockUser(roles = "PATIENT")
    void patientRole_CanAccessPatient_NotForbidden() throws Exception {
        // Resource may not exist (404) but security should pass (not 401/403)
        mockMvc.perform(get("/patients/1"))
                .andExpect(status().isNotFound()); // 404 means security passed, resource not found
    }
}
