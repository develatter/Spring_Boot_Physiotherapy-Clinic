package com.develatter.fisioclinic.infraestructure.persistence.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataAccountRepository extends JpaRepository<AccountEntity, UUID> {
}
