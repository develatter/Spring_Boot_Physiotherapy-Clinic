package com.develatter.fisioclinic.application.port.out.read;

import com.develatter.fisioclinic.domain.model.Therapist;

import java.util.Optional;
import java.util.UUID;

public interface LoadTherapistPort {
    Optional<Therapist> findById(UUID therapistId);
    Optional<Therapist> findByAccountId(UUID accountId);
}
