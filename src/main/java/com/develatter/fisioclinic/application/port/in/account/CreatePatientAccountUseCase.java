package com.develatter.fisioclinic.application.port.in.account;

import com.develatter.fisioclinic.infraestructure.controller.dto.request.CreatePatientAccountRequest;
import com.develatter.fisioclinic.infraestructure.controller.dto.response.CreateAccountResponse;

public interface CreatePatientAccountUseCase {
    CreateAccountResponse createPatientAccount(CreatePatientAccountRequest request);
}

