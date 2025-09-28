package com.develatter.fisioclinic.infraestructure.config;

import com.develatter.fisioclinic.domain.exception.PatientNotFoundException;
import com.develatter.fisioclinic.domain.exception.ServiceNotFoundException;
import com.develatter.fisioclinic.domain.exception.TherapistNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.status(400).body("Bad request: " + ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(500).body("Internal server error: " + ex.getMessage());
    }

    @ExceptionHandler(ServiceNotFoundException.class)
    public ResponseEntity<String> handleServiceNotFoundException(ServiceNotFoundException ex) {
        return ResponseEntity.status(404).body("Service not found: " + ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return ResponseEntity.status(400).body("Data integrity violation: " + ex.getMessage());
    }
}
