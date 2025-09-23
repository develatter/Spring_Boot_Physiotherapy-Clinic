package com.develatter.fisioclinic.application.port.in;

import com.develatter.fisioclinic.infraestructure.controller.dto.CreateTherapistAccountRequest;
import com.develatter.fisioclinic.infraestructure.controller.dto.CreateAccountResponse;

public interface CreateTherapistAccountUseCase {
    CreateAccountResponse createTherapistAccount(CreateTherapistAccountRequest request);
}

