package com.develatter.fisioclinic.domain.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.TimeZone;
import java.util.UUID;

public record AvailabilityRule(
        UUID id,
        Therapist therapist,
        DayOfWeek weekDay,
        LocalTime startTime,
        LocalTime endTime,
        TimeZone timeZone,
        LocalDate validFrom,
        LocalDate validTo
) {
}
