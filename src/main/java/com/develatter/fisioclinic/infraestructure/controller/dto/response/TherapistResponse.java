package com.develatter.fisioclinic.infraestructure.controller.dto.response;


import java.time.OffsetDateTime;
import java.util.Set;

public record TherapistResponse(
        String licenseNumber,
        String firstName,
        String lastName,
        String email,
        OffsetDateTime createdAt,
        boolean active,
        boolean enabled,
        Set<RoleResponse> role,
        Set<ServiceResponse> services
) {
}
