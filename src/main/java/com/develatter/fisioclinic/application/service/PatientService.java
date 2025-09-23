package com.develatter.fisioclinic.application.service;

import com.develatter.fisioclinic.application.port.in.patient.GetPatientUseCase;
import com.develatter.fisioclinic.application.port.in.patient.ListPatientsUseCase;
import com.develatter.fisioclinic.application.port.out.read.LoadAllPatientsPort;
import com.develatter.fisioclinic.application.port.out.read.LoadPatientPort;
import com.develatter.fisioclinic.domain.model.Role;
import com.develatter.fisioclinic.infraestructure.controller.dto.response.PatientResponse;
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
        var patient = loadPatientPort.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        return new PatientResponse(
                patient.account().email(),
                patient.firstName(),
                patient.lastName(),
                patient.birthDate(),
                patient.phoneNumber(),
                patient.address(),
                patient.account().createdAt(),
                patient.account().enabled(),
                Set.of(Role.PATIENT)
        );

    }

    @Override
    public PatientResponse getPatientByAccountId(UUID accountId) {
        var patient = loadPatientPort.findByAccountId(accountId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        return new PatientResponse(
                patient.account().email(),
                patient.firstName(),
                patient.lastName(),
                patient.birthDate(),
                patient.phoneNumber(),
                patient.address(),
                patient.account().createdAt(),
                patient.account().enabled(),
                Set.of(Role.PATIENT)
        );
    }

    @Override
    public List<PatientResponse> getAllPatients() {
        var patients = loadAllPatientsPort.findAllPatients();
        return (patients == null || patients.isEmpty()) ?
                List.of()
                :
                patients.stream().map(patient -> new PatientResponse(
                        patient.account().email(),
                        patient.firstName(),
                        patient.lastName(),
                        patient.birthDate(),
                        patient.phoneNumber(),
                        patient.address(),
                        patient.account().createdAt(),
                        patient.account().enabled(),
                        Set.of(Role.PATIENT)
                )).toList();
    }
}
