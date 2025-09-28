package com.develatter.fisioclinic.application.service;

import com.develatter.fisioclinic.application.port.in.patient.GetPatientUseCase;
import com.develatter.fisioclinic.application.port.in.patient.ListPatientsUseCase;
import com.develatter.fisioclinic.application.port.out.read.LoadAllPatientsPort;
import com.develatter.fisioclinic.application.port.out.read.LoadPatientPort;
import com.develatter.fisioclinic.domain.exception.ErrorType;
import com.develatter.fisioclinic.domain.exception.PatientNotFoundException;
import com.develatter.fisioclinic.domain.model.Role;
import com.develatter.fisioclinic.infraestructure.controller.dto.response.PatientResponse;
import com.develatter.fisioclinic.infraestructure.controller.dto.response.RoleResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class PatientService implements GetPatientUseCase, ListPatientsUseCase {

    private final LoadPatientPort loadPatientPort;
    private final LoadAllPatientsPort loadAllPatientsPort;


    public PatientService(LoadPatientPort loadPatientPort, LoadAllPatientsPort loadAllPatientsPort) {
        this.loadPatientPort = loadPatientPort;
        this.loadAllPatientsPort = loadAllPatientsPort;
    }

    @Override
    public PatientResponse getPatientById(UUID patientId) {
        return loadPatientPort.findById(patientId)
                .map(this::toResponse)
                .orElseThrow(() -> new PatientNotFoundException(
                        ErrorType.ID,
                        patientId.toString())
                );
    }

    @Override
    public PatientResponse getPatientByAccountId(UUID accountId) {
        return loadPatientPort.findByAccountId(accountId)
                .map(this::toResponse)
                .orElseThrow(() -> new PatientNotFoundException(
                        ErrorType.ACCOUNT_ID,
                        accountId.toString())
                );
    }

    @Override
    public List<PatientResponse> getAllPatients() {
        var patients = loadAllPatientsPort.findAllPatients();
        return (patients == null || patients.isEmpty()) ?
                List.of()
                :
                patients.stream().map(this::toResponse).toList();
    }

    private PatientResponse toResponse(com.develatter.fisioclinic.domain.model.Patient patient) {
        return new PatientResponse(
                patient.account().email(),
                patient.firstName(),
                patient.lastName(),
                patient.birthDate(),
                patient.phoneNumber(),
                patient.address(),
                patient.account().createdAt(),
                patient.account().enabled(),
                mapRolesToRoleResponses(patient.account().roles())
        );
    }

    private Set<RoleResponse> mapRolesToRoleResponses(Set<Role> roles) {
        if (roles == null) return Set.of();
        return roles.stream()
                .map(role -> new RoleResponse(role.name(), role.name())) // Puedes personalizar la descripción si lo deseas
                .collect(java.util.stream.Collectors.toSet());
    }
}
