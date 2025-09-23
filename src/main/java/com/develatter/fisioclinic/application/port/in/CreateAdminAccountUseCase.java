package com.develatter.fisioclinic.application.port.in;

import com.develatter.fisioclinic.infraestructure.controller.dto.CreateAdminAccountRequest;
import com.develatter.fisioclinic.infraestructure.controller.dto.CreateAccountResponse;

public interface CreateAdminAccountUseCase {
    CreateAccountResponse createAdminAccount(CreateAdminAccountRequest request);
}

