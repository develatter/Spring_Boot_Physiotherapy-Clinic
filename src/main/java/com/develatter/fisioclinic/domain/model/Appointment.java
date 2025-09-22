package com.develatter.fisioclinic.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public record Appointment(
        UUID id,
        Patient patient,
        Therapist therapist,
        Service service,
        Account createdBy,
        OffsetDateTime startTime,
        OffsetDateTime endTime,
        AppointmentStatus status,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
