package com.develatter.fisioclinic.application.service;

import com.develatter.fisioclinic.application.port.out.read.LoadAllServicesPort;
import com.develatter.fisioclinic.application.port.out.read.LoadServicePort;
import com.develatter.fisioclinic.domain.exception.ServiceNotFoundException;
import com.develatter.fisioclinic.domain.model.Service;
import com.develatter.fisioclinic.infraestructure.controller.dto.response.ServiceResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServiceServiceTest {

    @Mock
    private LoadServicePort loadServicePort;

    @Mock
    private LoadAllServicesPort loadAllServicesPort;

    @InjectMocks
    private  ServiceService serviceService;


    @Test
    void testGetServiceById_ReturnsPatient() {
        UUID serviceId = UUID.randomUUID();
        Service service = new Service(
                serviceId,
                "Masaje básico",
                35,
                5,
                5,
                2000,
                "Masaje de un grupo muscular",
                true
        );

        when(loadServicePort.findById(serviceId)).thenReturn(Optional.of(service));
        ServiceResponse response = serviceService.getServiceById(serviceId);
        assertEquals("Masaje básico",response.name());
        assertEquals(35,response.durationInMinutes());
        assertEquals(5,response.bufferTimeBeforeInMinutes());
        assertEquals(5,response.bufferTimeAfterInMinutes());
        assertEquals(2000,response.priceInCents());
        assertEquals("Masaje de un grupo muscular",response.description());
        assertTrue(response.active());
    }

    @Test
    void testGetServiceById_ServiceNotFound() {
        UUID serviceId = UUID.randomUUID();
        when(loadServicePort.findById(serviceId)).thenReturn(Optional.empty());
        assertThrows(ServiceNotFoundException.class, () -> serviceService.getServiceById(serviceId));
    }

    @Test
    void testGetAllServices_ReturnsListOfServices() {
        UUID serviceId1 = UUID.randomUUID();
        Service service1 = new Service(
                serviceId1,
                "Masaje básico",
                35,
                5,
                5,
                2000,
                "Masaje de un grupo muscular",
                true
        );
        UUID serviceId2 = UUID.randomUUID();
        Service service2 = new Service(
                serviceId2,
                "Fisioterapia",
                50,
                10,
                10,
                5000,
                "Sesión de fisioterapia",
                true
        );

        when(loadAllServicesPort.findAllServices()).thenReturn(
                List.of(service1,service2)
        );

        var responses = serviceService.getAllServices();
        assertEquals(2,responses.size());

        var response1 = responses.getFirst();
        assertEquals("Masaje básico",response1.name());
        assertEquals(35,response1.durationInMinutes());
        assertEquals(5,response1.bufferTimeBeforeInMinutes());
        assertEquals(5,response1.bufferTimeAfterInMinutes());
        assertEquals(2000,response1.priceInCents());
        assertEquals("Masaje de un grupo muscular",response1.description());
        assertTrue(response1.active());

        var response2 = responses.get(1);
        assertEquals("Fisioterapia",response2.name());
        assertEquals(50,response2.durationInMinutes());
        assertEquals(10,response2.bufferTimeBeforeInMinutes());
        assertEquals(10,response2.bufferTimeAfterInMinutes());
        assertEquals(5000,response2.priceInCents());
        assertEquals("Sesión de fisioterapia",response2.description());
        assertTrue(response2.active());
    }

    @Test
    void testGetAllServices_EmptyList() {
        when(loadAllServicesPort.findAllServices()).thenReturn(List.of());
        var responses = serviceService.getAllServices();
        assertTrue(responses.isEmpty());
    }
}
