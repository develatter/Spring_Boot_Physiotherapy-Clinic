package com.develatter.fisioclinic.infraestructure.controller.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AdminAccountRequest(
        @NotBlank @Email String email,
        @NotBlank String password
) {}

