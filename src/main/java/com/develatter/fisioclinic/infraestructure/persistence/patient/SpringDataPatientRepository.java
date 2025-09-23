package com.develatter.fisioclinic.infraestructure.persistence.patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataPatientRepository extends JpaRepository<PatientEntity, UUID> {
}

