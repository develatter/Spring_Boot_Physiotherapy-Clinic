package com.develatter.fisioclinic.infraestructure.controller.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record CreatePatientAccountRequest(
        @NotBlank @Email String email,
        @NotBlank String password,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotNull LocalDate birthDate,
        String phoneNumber,
        String address
) {}

