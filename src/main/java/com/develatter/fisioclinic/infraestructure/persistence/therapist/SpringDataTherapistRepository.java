package com.develatter.fisioclinic.infraestructure.persistence.therapist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataTherapistRepository extends JpaRepository<TherapistEntity, UUID> {
}

