package com.develatter.fisioclinic.infraestructure.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateAdminAccountRequest(
        @NotBlank @Email String email,
        @NotBlank String password
) {}

