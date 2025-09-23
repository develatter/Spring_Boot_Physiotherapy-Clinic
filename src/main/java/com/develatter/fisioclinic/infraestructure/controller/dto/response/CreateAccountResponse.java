package com.develatter.fisioclinic.infraestructure.controller.dto.response;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

public record CreateAccountResponse(
        UUID id,
        String email,
        boolean enabled,
        Set<String> roles,
        OffsetDateTime createdAt
) {
}
