package com.develatter.fisioclinic.application.port.in.account;

import com.develatter.fisioclinic.infraestructure.controller.dto.request.TherapistAccountRequest;
import com.develatter.fisioclinic.infraestructure.controller.dto.response.AccountResponse;

public interface CreateTherapistAccountUseCase {
    AccountResponse createTherapistAccount(TherapistAccountRequest request);
}

