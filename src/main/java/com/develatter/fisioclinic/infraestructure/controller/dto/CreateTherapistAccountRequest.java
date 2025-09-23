package com.develatter.fisioclinic.infraestructure.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateTherapistAccountRequest(
        @NotBlank @Email String email,
        @NotBlank String password,
        @NotBlank String licenseNumber,
        @NotBlank String firstName,
        @NotBlank String lastName
) {}

