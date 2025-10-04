package com.develatter.fisioclinic.infraestructure.controller.dto.request;

import jakarta.validation.constraints.NotNull;

public record AvailabilityRuleRequest(
        @NotNull TherapistAccountRequest therapist
) {
}
