package com.develatter.fisioclinic.application.service;

import com.develatter.fisioclinic.application.port.out.read.LoadAllPatientsPort;
import com.develatter.fisioclinic.application.port.out.read.LoadPatientPort;
import com.develatter.fisioclinic.domain.model.Account;
import com.develatter.fisioclinic.domain.model.Patient;
import com.develatter.fisioclinic.domain.exception.PatientNotFoundException;
import com.develatter.fisioclinic.domain.model.Role;
import com.develatter.fisioclinic.infraestructure.controller.dto.response.PatientResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {
    @Mock
    private LoadPatientPort loadPatientPort;
    @Mock
    private LoadAllPatientsPort loadAllPatientsPort;

    @InjectMocks
    private PatientService patientService;

    @Test
    void testGetPatientById_ReturnsPatient() {
        UUID patientId = UUID.randomUUID();
        OffsetDateTime now = OffsetDateTime.now();
        Account account = new Account(
                UUID.randomUUID(),
                "juan@correo.com",
                "hash",
                true,
                now,
                OffsetDateTime.now(),
                Set.of(Role.PATIENT)
        );
        Patient patient = new Patient(
                patientId,
                account,
                "Juan",
                "Perez",
                LocalDate.of(1990,1,1),
                "123456789",
                "Calle Falsa 123"
        );
        when(loadPatientPort.findById(patientId)).thenReturn(Optional.of(patient));

        PatientResponse response = patientService.getPatientById(patientId);
        assertEquals("Juan", response.firstName());
        assertEquals("Perez", response.lastName());
        assertEquals("juan@correo.com", response.email());
        assertEquals(LocalDate.of(1990,1,1), response.birthDate());
        assertEquals("123456789", response.phoneNumber());
        assertEquals("Calle Falsa 123", response.address());
        assertTrue(response.enabled());
        assertTrue(response.role().contains(Role.PATIENT));
        assertEquals(now, response.createdAt());
    }

    @Test
    void testGetPatientById_NotFound_ThrowsException() {
        UUID patientId = UUID.randomUUID();
        when(loadPatientPort.findById(patientId)).thenReturn(Optional.empty());
        assertThrows(PatientNotFoundException.class, () -> patientService.getPatientById(patientId));
    }

    @Test
    void testGetPatientByAccountId_ReturnsPatient() {
        UUID accountId = UUID.randomUUID();
        OffsetDateTime now = OffsetDateTime.now();
        Account account = new Account(
                accountId,
                "maria@correo.com",
                "hash",
                true,
                now,
                OffsetDateTime.now(),
                Set.of(Role.PATIENT)
        );
        Patient patient = new Patient(
                UUID.randomUUID(),
                account,
                "Maria",
                "Lopez",
                LocalDate.of(1985,5,5),
                "987654321",
                "Av. Siempre Viva 742");
        when(loadPatientPort.findByAccountId(accountId)).thenReturn(Optional.of(patient));

        PatientResponse response = patientService.getPatientByAccountId(accountId);
        assertEquals("Maria", response.firstName());
        assertEquals("Lopez", response.lastName());
        assertEquals("maria@correo.com", response.email());
        assertEquals(LocalDate.of(1985,5,5), response.birthDate());
        assertEquals("987654321", response.phoneNumber());
        assertEquals("Av. Siempre Viva 742", response.address());
        assertTrue(response.enabled());
        assertTrue(response.role().contains(Role.PATIENT));
        assertEquals(now, response.createdAt());
    }

    @Test
    void testGetPatientByAccountId_NotFound_ThrowsException() {
        UUID accountId = UUID.randomUUID();
        when(loadPatientPort.findByAccountId(accountId)).thenReturn(Optional.empty());
        assertThrows(PatientNotFoundException.class, () -> patientService.getPatientByAccountId(accountId));
    }

    @Test
    void testGetAllPatients_ReturnsList() {
        Account account1 = new Account(
                UUID.randomUUID(),
                "a@a.com",
                "hash",
                true,
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                Set.of(Role.PATIENT)
        );
        Account account2 = new Account(
                UUID.randomUUID(),
                "b@b.com",
                "hash",
                true,
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                Set.of(Role.PATIENT)
        );
        Patient patient1 = new Patient(
                UUID.randomUUID(),
                account1,
                "Ana",
                "Diaz",
                LocalDate.of(1992,2,2),
                "111111111",
                "Calle 1"
        );
        Patient patient2 = new Patient(
                UUID.randomUUID(),
                account2,
                "Luis",
                "Martinez",
                LocalDate.of(1988,8,8),
                "222222222",
                "Calle 2");
        when(loadAllPatientsPort.findAllPatients()).thenReturn(List.of(patient1, patient2));

        var result = patientService.getAllPatients();
        assertEquals(2, result.size());
        assertEquals("Ana", result.get(0).firstName());
        assertEquals("Luis", result.get(1).firstName());
    }

    @Test
    void testGetAllPatients_EmptyList() {
        when(loadAllPatientsPort.findAllPatients()).thenReturn(List.of());
        var result = patientService.getAllPatients();
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetPatientById_NullFields() {
        UUID patientId = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class, () -> {
            Account account = new Account(
                UUID.randomUUID(),
                null,
                null,
                true,
                null,
                null,
                Set.of()
            );
            new Patient(
                patientId,
                account,
                null,
                null,
                null,
                null,
                null
            );
        });
    }

    @Test
    void testGetPatientByAccountId_NullFields() {
        UUID patientId = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class, () -> {
            Account account = new Account(
                UUID.randomUUID(),
                null,
                null,
                true,
                null,
                null,
                Set.of()
            );
            new Patient(
                patientId,
                account,
                null,
                null,
                null,
                null,
                null
            );
        });
    }

    @Test
    void testGetAllPatients_NullFields() {
        Account account1 = new Account(UUID.randomUUID(), null, null, true, null, null, Set.of(Role.PATIENT));
        Patient patient1 = new Patient(UUID.randomUUID(), account1, null, null, null, null, null);
        Account account2 = new Account(UUID.randomUUID(), null, null, true, null, null, Set.of(Role.PATIENT));
        Patient patient2 = new Patient(UUID.randomUUID(), account2, null, null, null, null, null);

        when(loadAllPatientsPort.findAllPatients()).thenReturn(List.of(patient1, patient2));

        var result = patientService.getAllPatients();
        assertEquals(2, result.size());

        assertNull(result.getFirst().firstName());
        assertNull(result.getFirst().email());
        assertNull(result.getFirst().birthDate());
        assertNull(result.getFirst().phoneNumber());
        assertNull(result.getFirst().address());
        assertTrue(result.getFirst().role().contains(Role.PATIENT));

        assertNull(result.get(1).firstName());
        assertNull(result.get(1).email());
        assertNull(result.get(1).birthDate());
        assertNull(result.get(1).phoneNumber());
        assertNull(result.get(1).address());
        assertTrue(result.get(1).role().contains(Role.PATIENT));
    }

    @Test
    void testGetPatientById_PortThrowsException() {
        UUID patientId = UUID.randomUUID();
        when(loadPatientPort.findById(patientId)).thenThrow(new RuntimeException("DB error"));
        assertThrows(RuntimeException.class, () -> patientService.getPatientById(patientId));
    }

    @Test
    void testGetPatientByAccountId_PortThrowsException() {
        UUID accountId = UUID.randomUUID();
        when(loadPatientPort.findByAccountId(accountId)).thenThrow(new RuntimeException("DB error"));
        assertThrows(RuntimeException.class, () -> patientService.getPatientByAccountId(accountId));
    }

    @Test
    void testGetAllPatients_PortThrowsException() {
        when(loadAllPatientsPort.findAllPatients()).thenThrow(new RuntimeException("DB error"));
        assertThrows(RuntimeException.class, () -> patientService.getAllPatients());
    }

    @Test
    void testGetAllPatients_NullResult() {
        when(loadAllPatientsPort.findAllPatients()).thenReturn(null);
        var result = patientService.getAllPatients();
        assertTrue(result.isEmpty());
    }
}
