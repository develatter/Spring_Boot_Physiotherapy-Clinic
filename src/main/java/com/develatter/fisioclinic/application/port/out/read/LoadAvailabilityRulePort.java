package com.develatter.fisioclinic.application.port.out.read;

import com.develatter.fisioclinic.domain.model.AvailabilityRule;

import java.util.Optional;
import java.util.UUID;

public interface LoadAvailabilityRulePort {
    Optional<AvailabilityRule> findAvailabilityRuleById(UUID id);
    Optional<AvailabilityRule> findByTherapistId(UUID therapistId);
}
