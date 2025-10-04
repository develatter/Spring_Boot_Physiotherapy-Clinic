package com.develatter.fisioclinic.infraestructure.persistence.availability_rule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringDataAvailabilityRuleRepository extends JpaRepository<AvailabilityRuleEntity, UUID> {
    Optional<AvailabilityRuleEntity> findByTherapistId(UUID therapistId);
}
