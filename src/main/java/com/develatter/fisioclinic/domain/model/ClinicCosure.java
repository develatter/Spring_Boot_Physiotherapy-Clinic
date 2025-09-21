package com.develatter.fisioclinic.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ClinicCosure(
        UUID id,
        OffsetDateTime startTime,
        OffsetDateTime endTime,
        String reason
) {
}
