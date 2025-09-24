package com.develatter.fisioclinic.domain.exception;

public class PatientNotFoundException extends RuntimeException {

    public PatientNotFoundException(ErrorType type, String value) {
        super(
                String.format("Patient not found with %s : %s" ,type.name().toLowerCase(), value)
        );
    }
}
