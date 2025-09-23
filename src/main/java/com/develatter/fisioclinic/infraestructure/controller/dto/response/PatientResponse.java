package com.develatter.fisioclinic.infraestructure.controller.dto.response;

import com.develatter.fisioclinic.domain.model.Role;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;

public record PatientResponse(
        String email,
        String firstName,
        String lastName,
        LocalDate birthDate,
        String phoneNumber,
        String address,
        OffsetDateTime createdAt,
        boolean enabled,
        Set<Role> role
) {}