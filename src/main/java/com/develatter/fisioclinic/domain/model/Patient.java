package com.develatter.fisioclinic.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public record Patient(
        UUID id,
        Account account,
        String firstName,
        String lastName,
        LocalDate birthDate,
        String phoneNumber,
        String address
) {
}
