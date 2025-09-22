package com.develatter.fisioclinic.domain.model;

import java.util.UUID;

public record Service(
        UUID id,
        String name,
        int durationInMinutes,
        int bufferTimeBeforeInMinutes,
        int bufferTimeAfterInMinutes,
        int priceInCents,
        String description,
        boolean active
) {
}
