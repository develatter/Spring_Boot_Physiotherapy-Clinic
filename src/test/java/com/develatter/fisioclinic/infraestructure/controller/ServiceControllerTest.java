package com.develatter.fisioclinic.infraestructure.controller;

import com.develatter.fisioclinic.application.service.ServiceService;
import com.develatter.fisioclinic.infraestructure.controller.dto.response.ServiceResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

@WebMvcTest(ServiceController.class)
public class ServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ServiceService serviceService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public ServiceService serviceService() {
            return Mockito.mock(ServiceService.class);
        }
    }

    @Test
    @WithMockUser
    void testGetServiceById_ReturnsService() throws Exception {
        UUID serviceId = UUID.randomUUID();
        ServiceResponse response = new ServiceResponse(
                "Masaje básico",
                35,
                5,
                5,
                2000,
                "Masaje de un grupo muscular",
                true
        );
        Mockito.when(serviceService.getServiceById(serviceId)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/services/" + serviceId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Masaje básico"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.durationInMinutes").value(35))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bufferTimeBeforeInMinutes").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bufferTimeAfterInMinutes").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceInCents").value(2000))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Masaje de un grupo muscular"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(true));

    }

    @Test
    @WithMockUser
    void testGetServiceById_NotFound() throws Exception {
        UUID serviceId = UUID.randomUUID();
        Mockito.when(serviceService.getServiceById(serviceId)).thenThrow(new RuntimeException("Not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/services/" + serviceId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }

    @Test
    @WithMockUser
    void testGetAllServices_ReturnsList() throws Exception {
        ServiceResponse s1 = new ServiceResponse("Masaje", 30, 5, 5, 1500, "Masaje", true);
        ServiceResponse s2 = new ServiceResponse("Rehabilitación", 60, 10, 10, 3000, "Rehab", true);
        Mockito.when(serviceService.getAllServices()).thenReturn(List.of(s1, s2));

        mockMvc.perform(MockMvcRequestBuilders.get("/services/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Masaje"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Rehabilitación"));
    }

    @Test
    @WithMockUser
    void testGetAllServices_EmptyList() throws Exception {
        Mockito.when(serviceService.getAllServices()).thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/services/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }

    @Test
    void testGetServiceById_Unauthorized() throws Exception {
        UUID serviceId = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.get("/services/" + serviceId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void testGetServiceById_InvalidUUID() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/services/invalid-uuid")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser
    void testGetServiceById_InternalError() throws Exception {
        UUID serviceId = UUID.randomUUID();
        Mockito.when(serviceService.getServiceById(serviceId)).thenThrow(new RuntimeException("Internal error"));
        mockMvc.perform(MockMvcRequestBuilders.get("/services/" + serviceId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

}
