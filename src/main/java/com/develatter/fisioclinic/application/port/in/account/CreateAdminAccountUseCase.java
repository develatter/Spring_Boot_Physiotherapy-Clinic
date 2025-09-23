package com.develatter.fisioclinic.application.port.in.account;

import com.develatter.fisioclinic.infraestructure.controller.dto.request.CreateAdminAccountRequest;
import com.develatter.fisioclinic.infraestructure.controller.dto.response.CreateAccountResponse;

public interface CreateAdminAccountUseCase {
    CreateAccountResponse createAdminAccount(CreateAdminAccountRequest request);
}

