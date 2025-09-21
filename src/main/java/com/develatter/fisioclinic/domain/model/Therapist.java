package com.develatter.fisioclinic.domain.model;

import java.util.UUID;

public record Therapist(
        UUID id,
        Account account,
        String licenseNumber,
        String firstName,
        String lastName,
        boolean active
) {
}
