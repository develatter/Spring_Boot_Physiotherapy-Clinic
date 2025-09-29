package com.develatter.fisioclinic.infraestructure.controller;

import com.develatter.fisioclinic.application.service.TherapistService;
import com.develatter.fisioclinic.infraestructure.controller.dto.response.TherapistResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

@WebMvcTest(TherapistController.class)
class TherapistControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TherapistService therapistService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public TherapistService therapistService() {
            return Mockito.mock(TherapistService.class);
        }
    }

    TherapistResponse sampleTherapist() {
        return new TherapistResponse("1234", "Ana", "López", "ana@correo.com", OffsetDateTime.now(), true, true, Set.of(), Set.of());
    }

    @Test
    @WithMockUser
    void testGetTherapistById_ReturnsTherapist() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(therapistService.getTherapistById(id)).thenReturn(sampleTherapist());
        mockMvc.perform(MockMvcRequestBuilders.get("/therapist/id/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Ana"));
    }

    @Test
    @WithMockUser
    void testGetTherapistById_NotFound() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(therapistService.getTherapistById(id)).thenThrow(new NoSuchElementException("Not found"));
        mockMvc.perform(MockMvcRequestBuilders.get("/therapist/id/" + id))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }

    @Test
    @WithMockUser
    void testGetTherapistById_InvalidUUID() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/therapist/id/invalid-uuid"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testGetTherapistById_Unauthorized() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.get("/therapist/id/" + id))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void testGetTherapistById_InternalError() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(therapistService.getTherapistById(id)).thenThrow(new RuntimeException("Internal error"));
        mockMvc.perform(MockMvcRequestBuilders.get("/therapist/id/" + id))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    @WithMockUser
    void testGetTherapistByAccountId_ReturnsTherapist() throws Exception {
        UUID accountId = UUID.randomUUID();
        Mockito.when(therapistService.getTherapistByAccountId(accountId)).thenReturn(sampleTherapist());
        mockMvc.perform(MockMvcRequestBuilders.get("/therapist/account-id/" + accountId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Ana"));
    }

    @Test
    @WithMockUser
    void testGetTherapistByAccountId_NotFound() throws Exception {
        UUID accountId = UUID.randomUUID();
        Mockito.when(therapistService.getTherapistByAccountId(accountId)).thenThrow(new NoSuchElementException("Not found"));
        mockMvc.perform(MockMvcRequestBuilders.get("/therapist/account-id/" + accountId))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }

    @Test
    @WithMockUser
    void testGetTherapistByAccountId_InvalidUUID() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/therapist/account-id/invalid-uuid"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testGetTherapistByAccountId_Unauthorized() throws Exception {
        UUID accountId = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.get("/therapist/account-id/" + accountId))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void testGetTherapistByAccountId_InternalError() throws Exception {
        UUID accountId = UUID.randomUUID();
        Mockito.when(therapistService.getTherapistByAccountId(accountId)).thenThrow(new RuntimeException("Internal error"));
        mockMvc.perform(MockMvcRequestBuilders.get("/therapist/account-id/" + accountId))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    @WithMockUser
    void testGetAllTherapists_ReturnsList() throws Exception {
        TherapistResponse t1 = sampleTherapist();
        TherapistResponse t2 = new TherapistResponse("5678", "Luis", "Pérez", "luis@correo.com", OffsetDateTime.now(), true, true, Set.of(), Set.of());
        Mockito.when(therapistService.getAllTherapists()).thenReturn(List.of(t1, t2));
        mockMvc.perform(MockMvcRequestBuilders.get("/therapist/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("Ana"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName").value("Luis"));
    }

    @Test
    @WithMockUser
    void testGetAllTherapists_EmptyList() throws Exception {
        Mockito.when(therapistService.getAllTherapists()).thenReturn(List.of());
        mockMvc.perform(MockMvcRequestBuilders.get("/therapist/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }
}

