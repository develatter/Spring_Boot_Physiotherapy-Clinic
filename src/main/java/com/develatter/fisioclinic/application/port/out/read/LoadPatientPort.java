package com.develatter.fisioclinic.application.port.out.read;

import com.develatter.fisioclinic.domain.model.Patient;

import java.util.Optional;
import java.util.UUID;

public interface LoadPatientPort {
    boolean existsById(UUID patientId);
    Optional<Patient> findById(UUID patientId);
    Optional<Patient> findByAccountId(UUID accountId);
}
