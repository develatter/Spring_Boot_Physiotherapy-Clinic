package com.develatter.fisioclinic.application.port.in.account;

import com.develatter.fisioclinic.infraestructure.controller.dto.request.AdminAccountRequest;
import com.develatter.fisioclinic.infraestructure.controller.dto.response.AccountResponse;

public interface CreateAdminAccountUseCase {
    AccountResponse createAdminAccount(AdminAccountRequest request);
}

