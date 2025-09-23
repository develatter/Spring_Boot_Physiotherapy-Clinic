package com.develatter.fisioclinic.application.port.in.patient;


import com.develatter.fisioclinic.infraestructure.controller.dto.response.PatientResponse;

import java.util.UUID;

public interface GetPatientUseCase {
    PatientResponse getPatientById(UUID patientId);
    PatientResponse getPatientByAccountId(UUID accountId);
}
