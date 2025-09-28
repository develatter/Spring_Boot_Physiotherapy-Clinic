package com.develatter.fisioclinic.infraestructure.controller.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ServiceRequest(
        @NotBlank String name,
        @Min(0) int durationInMinutes,
        @Min(0) int bufferTimeBeforeInMinutes,
        @Min(0) int bufferTimeAfterInMinutes,
        @Min(0) int priceInCents,
        @NotBlank String description,
        boolean active
) {
}
