package com.develatter.fisioclinic.infraestructure.controller.dto.response;

import com.develatter.fisioclinic.domain.model.Role;
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
        Set<Role> role
) {
}
