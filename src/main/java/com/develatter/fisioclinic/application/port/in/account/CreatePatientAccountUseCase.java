package com.develatter.fisioclinic.application.port.in.account;

import com.develatter.fisioclinic.infraestructure.controller.dto.request.PatientAccountRequest;
import com.develatter.fisioclinic.infraestructure.controller.dto.response.AccountResponse;

public interface CreatePatientAccountUseCase {
    AccountResponse createPatientAccount(PatientAccountRequest request);
}

