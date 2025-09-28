package com.develatter.fisioclinic.infraestructure.controller.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record TherapistAccountRequest(
        @NotBlank @Email String email,
        @NotBlank String password,
        @NotBlank String licenseNumber,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotNull Set<ServiceRequest> services
        ) {}

