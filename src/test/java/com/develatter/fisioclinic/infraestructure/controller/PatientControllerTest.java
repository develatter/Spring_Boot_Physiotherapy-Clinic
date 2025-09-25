package com.develatter.fisioclinic.infraestructure.controller;

import com.develatter.fisioclinic.application.service.PatientService;
import com.develatter.fisioclinic.domain.exception.ErrorType;
import com.develatter.fisioclinic.domain.exception.PatientNotFoundException;
import com.develatter.fisioclinic.domain.model.Role;
import com.develatter.fisioclinic.infraestructure.controller.dto.response.PatientResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
class PatientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PatientService patientService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public PatientService patientService() {
            return Mockito.mock(PatientService.class);
        }
    }

    @Test
    @WithMockUser
    void testGetPatientById_ReturnsPatient() throws Exception {
        UUID patientId = UUID.randomUUID();
        PatientResponse response = new PatientResponse(
            "juan@correo.com",
            "Juan",
            "Perez",
            LocalDate.of(1990, 1, 1),
            "123456789",
            "Calle Falsa 123",
            OffsetDateTime.now(),
            true,
            Set.of(Role.PATIENT)
        );
        Mockito.when(patientService.getPatientById(patientId)).thenReturn(response);

        mockMvc.perform(get("/patient/id/" + patientId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Juan"))
                .andExpect(jsonPath("$.lastName").value("Perez"))
                .andExpect(jsonPath("$.email").value("juan@correo.com"));
    }

    @Test
    @WithMockUser
    void testGetPatientById_NotFound() throws Exception {
        UUID patientId = UUID.randomUUID();
        Mockito.when(patientService.getPatientById(patientId)).thenThrow(new PatientNotFoundException(ErrorType.ID, patientId.toString()));

        mockMvc.perform(get("/patient/id/" + patientId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser
    void testGetPatientByAccountId_ReturnsPatient() throws Exception {
        UUID accountId = UUID.randomUUID();
        PatientResponse response = new PatientResponse(
            "maria@correo.com",
            "Maria",
            "Lopez",
            LocalDate.of(1985, 5, 5),
            "987654321",
            "Av. Siempre Viva 742",
            OffsetDateTime.now(),
            true,
            Set.of(Role.PATIENT)
        );
        Mockito.when(patientService.getPatientByAccountId(accountId)).thenReturn(response);

        mockMvc.perform(get("/patient/account-id/" + accountId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Maria"))
                .andExpect(jsonPath("$.lastName").value("Lopez"))
                .andExpect(jsonPath("$.email").value("maria@correo.com"));
    }

    @Test
    @WithMockUser
    void testGetPatientByAccountId_NotFound() throws Exception {
        UUID accountId = UUID.randomUUID();
        Mockito.when(patientService.getPatientByAccountId(accountId)).thenThrow(new PatientNotFoundException(com.develatter.fisioclinic.domain.exception.ErrorType.ACCOUNT_ID, accountId.toString()));

        mockMvc.perform(get("/patient/account-id/" + accountId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser
    void testGetAllPatients_ReturnsList() throws Exception {
        PatientResponse p1 = new PatientResponse(
            "a@a.com",
            "Ana",
            "Diaz",
            LocalDate.of(1992, 2, 2),
            "111111111",
            "Calle 1",
            OffsetDateTime.now(),
            true,
            Set.of(Role.PATIENT)
        );
        PatientResponse p2 = new PatientResponse(
            "b@b.com",
            "Luis",
            "Martinez",
            LocalDate.of(1988, 8, 8),
            "222222222",
            "Calle 2",
            OffsetDateTime.now(),
            true,
            Set.of(Role.PATIENT)
        );
        Mockito.when(patientService.getAllPatients()).thenReturn(List.of(p1, p2));

        mockMvc.perform(get("/patient/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Ana"))
                .andExpect(jsonPath("$[1].firstName").value("Luis"));
    }

    @Test
    @WithMockUser
    void testGetAllPatients_EmptyList() throws Exception {
        Mockito.when(patientService.getAllPatients()).thenReturn(List.of());

        mockMvc.perform(get("/patient/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void testGetPatientById_Unauthorized() throws Exception {
        UUID patientId = UUID.randomUUID();
        mockMvc.perform(get("/patient/id/" + patientId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void testGetPatientById_InvalidUUID() throws Exception {
        mockMvc.perform(get("/patient/id/invalid-uuid")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void testGetPatientById_InternalError() throws Exception {
        UUID patientId = UUID.randomUUID();
        Mockito.when(patientService.getPatientById(patientId)).thenThrow(new RuntimeException("Internal error"));
        mockMvc.perform(get("/patient/id/" + patientId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}
