package com.develatter.fisioclinic.application.service;

import com.develatter.fisioclinic.domain.exception.TherapistNotFoundException;
import com.develatter.fisioclinic.domain.model.Therapist;
import com.develatter.fisioclinic.infraestructure.controller.dto.response.TherapistResponse;
import com.develatter.fisioclinic.application.port.out.read.LoadTherapistPort;
import com.develatter.fisioclinic.application.port.out.read.LoadAllTherapistsPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.develatter.fisioclinic.domain.model.Service;
import com.develatter.fisioclinic.domain.model.Account;
import com.develatter.fisioclinic.domain.model.Role;

class TherapistServiceTest {
    private LoadTherapistPort loadTherapistPort;
    private LoadAllTherapistsPort loadAllTherapistsPort;
    private TherapistService therapistService;

    @BeforeEach
    void setUp() {
        loadTherapistPort = mock(LoadTherapistPort.class);
        loadAllTherapistsPort = mock(LoadAllTherapistsPort.class);
        therapistService = new TherapistService(loadTherapistPort, loadAllTherapistsPort);
    }

    Therapist buildTherapist(String license, String first, String last, String email) {
        UUID therapistId = UUID.randomUUID();
        Account account = new Account(
            UUID.randomUUID(),
            email,
            "hash",
            true,
            OffsetDateTime.now(),
            OffsetDateTime.now(),
            Set.of(Role.THERAPIST)
        );
        Set<Service> services = Set.of();
        return new Therapist(
            therapistId,
            account,
            license,
            first,
            last,
            true,
            services
        );
    }

    @Test
    void getTherapistById_returnsTherapist() {
        UUID id = UUID.randomUUID();
        Therapist therapist = buildTherapist("1234", "Ana", "López", "ana@correo.com");
        when(loadTherapistPort.findById(id)).thenReturn(Optional.of(therapist));
        TherapistResponse result = therapistService.getTherapistById(id);
        assertEquals("Ana", result.firstName());
        assertEquals("López", result.lastName());
    }

    @Test
    void getTherapistById_notFound_throwsException() {
        UUID id = UUID.randomUUID();
        when(loadTherapistPort.findById(id)).thenReturn(Optional.empty());
        assertThrows(TherapistNotFoundException.class, () -> therapistService.getTherapistById(id));
    }

    @Test
    void getTherapistByAccountId_returnsTherapist() {
        UUID accountId = UUID.randomUUID();
        Therapist therapist = buildTherapist("5678", "Luis", "Pérez", "luis@correo.com");
        when(loadTherapistPort.findByAccountId(accountId)).thenReturn(Optional.of(therapist));
        TherapistResponse result = therapistService.getTherapistByAccountId(accountId);
        assertEquals("Luis", result.firstName());
        assertEquals("Pérez", result.lastName());
    }

    @Test
    void getTherapistByAccountId_notFound_throwsException() {
        UUID accountId = UUID.randomUUID();
        when(loadTherapistPort.findByAccountId(accountId)).thenReturn(Optional.empty());
        assertThrows(TherapistNotFoundException.class, () -> therapistService.getTherapistByAccountId(accountId));
    }

    @Test
    void getAllTherapists_returnsList() {
        Therapist t1 = buildTherapist("1", "Ana", "López", "ana@correo.com");
        Therapist t2 = buildTherapist("2", "Luis", "Pérez", "luis@correo.com");
        when(loadAllTherapistsPort.findAllTherapists()).thenReturn(List.of(t1, t2));
        List<TherapistResponse> result = therapistService.getAllTherapists();
        assertEquals(2, result.size());
        assertEquals("Ana", result.getFirst().firstName());
    }

    @Test
    void getAllTherapists_emptyList() {
        when(loadAllTherapistsPort.findAllTherapists()).thenReturn(List.of());
        List<TherapistResponse> result = therapistService.getAllTherapists();
        assertTrue(result.isEmpty());
    }
}
