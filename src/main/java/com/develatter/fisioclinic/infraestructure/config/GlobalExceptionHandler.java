package com.develatter.fisioclinic.infraestructure.config;

import com.develatter.fisioclinic.domain.exception.PatientNotFoundException;
import com.develatter.fisioclinic.domain.exception.TherapistNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<String> handlePatientNotFoundException(PatientNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(TherapistNotFoundException.class)
    public ResponseEntity<String> handleTherapistNotFoundException(TherapistNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }
}
