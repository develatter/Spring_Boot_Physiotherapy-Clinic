package com.develatter.fisioclinic.domain.model;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

public record Account(
        UUID id,
        String email,
        String passwordHash,
        boolean enabled,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        Set<Role> roles
) {}