package com.develatter.fisioclinic.infraestructure.persistence.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataRoleRepository extends JpaRepository<RoleEntity, String> {
}

