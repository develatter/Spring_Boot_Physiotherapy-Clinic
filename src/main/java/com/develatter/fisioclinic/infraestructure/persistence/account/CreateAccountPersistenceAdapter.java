package com.develatter.fisioclinic.infraestructure.persistence.account;

import com.develatter.fisioclinic.application.port.out.create.CreateAccountPort;
import com.develatter.fisioclinic.domain.model.Account;
import com.develatter.fisioclinic.domain.model.Role;
import com.develatter.fisioclinic.infraestructure.persistence.role.RoleEntity;
import com.develatter.fisioclinic.infraestructure.persistence.role.SpringDataRoleRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;


@Repository
public class CreateAccountPersistenceAdapter implements CreateAccountPort {

    private final SpringDataAccountRepository repository;
    private final SpringDataRoleRepository roleRepository;

    public CreateAccountPersistenceAdapter(SpringDataAccountRepository repository, SpringDataRoleRepository roleRepository) {
        this.repository = repository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Account save(Account account) {
        Set<RoleEntity> roleEntities = account.roles().stream()
                .map(role -> roleRepository.findById(role.name())
                        .orElseThrow(() -> new RuntimeException("Role not found: " + role.name())))
                .collect(Collectors.toSet());

        var entity = AccountEntity.builder()
                .id(account.id())
                .email(account.email())
                .passwordHash(account.passwordHash())
                .enabled(account.enabled())
                .createdAt(account.createdAt())
                .updatedAt(account.updatedAt())
                .roles(roleEntities)
                .build();
        var savedEntity = repository.save(entity);

        if (savedEntity.getRoles() == null || savedEntity.getRoles().isEmpty()) {
            throw new RuntimeException("Failed to save account roles");
        }

        return new Account(
                savedEntity.getId(),
                savedEntity.getEmail(),
                savedEntity.getPasswordHash(),
                savedEntity.isEnabled(),
                savedEntity.getCreatedAt(),
                savedEntity.getUpdatedAt(),
                savedEntity.getRoles().stream().map(
                        e -> switch (e.getCode()) {
                            case "THERAPIST" -> Role.THERAPIST;
                            case "ADMIN" -> Role.ADMIN;
                            default -> Role.PATIENT;
                        }
                ).collect(Collectors.toSet())
        );
    }
}
