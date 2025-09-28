package com.develatter.fisioclinic.infraestructure.persistence.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataServiceRepository extends JpaRepository<ServiceEntity, UUID> {
}
