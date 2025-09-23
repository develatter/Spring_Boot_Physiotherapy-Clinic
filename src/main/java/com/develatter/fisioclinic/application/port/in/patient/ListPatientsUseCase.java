package com.develatter.fisioclinic.application.port.in.patient;

import com.develatter.fisioclinic.infraestructure.controller.dto.response.PatientResponse;

import java.util.List;

public interface ListPatientsUseCase {
    List<PatientResponse> getAllPatients();
}
