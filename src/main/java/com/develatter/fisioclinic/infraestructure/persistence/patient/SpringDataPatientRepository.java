package com.develatter.fisioclinic.infraestructure.persistence.patient;

import com.develatter.fisioclinic.domain.model.Patient;
import com.develatter.fisioclinic.infraestructure.persistence.account.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringDataPatientRepository extends JpaRepository<PatientEntity, UUID> {
    Optional<PatientEntity> findByAccountId(UUID uuid);
}
