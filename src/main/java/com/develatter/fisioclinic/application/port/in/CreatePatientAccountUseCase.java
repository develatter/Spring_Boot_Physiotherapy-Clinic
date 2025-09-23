package com.develatter.fisioclinic.application.port.in;

import com.develatter.fisioclinic.infraestructure.controller.dto.CreatePatientAccountRequest;
import com.develatter.fisioclinic.infraestructure.controller.dto.CreateAccountResponse;

public interface CreatePatientAccountUseCase {
    CreateAccountResponse createPatientAccount(CreatePatientAccountRequest request);
}

