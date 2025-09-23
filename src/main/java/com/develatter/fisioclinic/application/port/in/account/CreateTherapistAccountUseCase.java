package com.develatter.fisioclinic.application.port.in.account;

import com.develatter.fisioclinic.infraestructure.controller.dto.request.CreateTherapistAccountRequest;
import com.develatter.fisioclinic.infraestructure.controller.dto.response.CreateAccountResponse;

public interface CreateTherapistAccountUseCase {
    CreateAccountResponse createTherapistAccount(CreateTherapistAccountRequest request);
}

