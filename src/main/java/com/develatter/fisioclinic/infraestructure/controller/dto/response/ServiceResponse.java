package com.develatter.fisioclinic.infraestructure.controller.dto.response;

public record ServiceResponse(
        String name,
        int durationInMinutes,
        int bufferTimeBeforeInMinutes,
        int bufferTimeAfterInMinutes,
        int priceInCents,
        String description,
        boolean active
) {
}
