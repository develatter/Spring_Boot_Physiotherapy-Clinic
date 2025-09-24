package com.develatter.fisioclinic.domain.exception;

public class TherapistNotFoundException extends RuntimeException {

    public TherapistNotFoundException(ErrorType type, String value) {
        super(
                String.format("Therapist not found with %s : %s" ,type.name().toLowerCase(), value)
        );
    }
}
