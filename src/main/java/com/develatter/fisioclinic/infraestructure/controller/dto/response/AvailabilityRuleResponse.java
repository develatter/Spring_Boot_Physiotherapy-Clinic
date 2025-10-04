package com.develatter.fisioclinic.infraestructure.controller.dto.response;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.TimeZone;

public record AvailabilityRuleResponse(
        DayOfWeek weekday,
        TherapistResponse therapist,
        LocalTime startTime,
        LocalTime endTime,
        TimeZone timeZone,
        LocalDate validFrom,
        LocalDate validTo
) {
}
