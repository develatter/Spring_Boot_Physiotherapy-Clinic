package com.develatter.fisioclinic.infraestructure.persistence.account;

import com.develatter.fisioclinic.application.port.out.CreateAccountPort;
import com.develatter.fisioclinic.domain.model.Account;
import com.develatter.fisioclinic.domain.model.Role;
import org.springframework.stereotype.Repository;

import java.util.stream.Collectors;


@Repository
public class CreateAccountRepositoryAdapter implements CreateAccountPort {

    private final SpringDataAccountRepository repository;

    public CreateAccountRepositoryAdapter(SpringDataAccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public Account save(Account account) {

        var entity = AccountEntity.builder()
                .id(account.id())
                .email(account.email())
                .passwordHash(account.passwordHash())
                .createdAt(account.createdAt())
                .updatedAt(account.updatedAt())
                .build();
        var savedEntity = repository.save(entity);

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
