package com.develatter.fisioclinic.infraestructure.persistence.therapist;

import com.develatter.fisioclinic.application.port.out.create.CreateTherapistPort;
import com.develatter.fisioclinic.application.port.out.read.LoadAllTherapistsPort;
import com.develatter.fisioclinic.application.port.out.read.LoadTherapistPort;
import com.develatter.fisioclinic.domain.model.Role;
import com.develatter.fisioclinic.domain.model.Therapist;
import com.develatter.fisioclinic.infraestructure.persistence.account.AccountEntity;
import com.develatter.fisioclinic.domain.model.Account;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component
public class TherapistPersistenceAdapter implements CreateTherapistPort, LoadTherapistPort, LoadAllTherapistsPort {
    private final SpringDataTherapistRepository therapistRepository;

    public TherapistPersistenceAdapter(SpringDataTherapistRepository therapistRepository) {
        this.therapistRepository = therapistRepository;
    }

    @Override
    public Therapist save(Therapist therapist) {
        TherapistEntity entity = toEntity(therapist);
        TherapistEntity saved = therapistRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public List<Therapist> findAllTherapists() {
        return therapistRepository
                .findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<Therapist> findById(UUID therapistId) {
        return therapistRepository
                .findById(therapistId)
                .map(this::toDomain);
    }

    @Override
    public Optional<Therapist> findByAccountId(UUID accountId) {
        return therapistRepository
                .findByAccountId(accountId)
                .map(this::toDomain);
    }

    private TherapistEntity toEntity(Therapist therapist) {
        return TherapistEntity.builder()
                .id(therapist.id())
                .account(AccountEntity.builder().id(therapist.account().id()).build())
                .licenseNumber(therapist.licenseNumber())
                .firstName(therapist.firstName())
                .lastName(therapist.lastName())
                .active(therapist.active())
                .build();
    }

    private Therapist toDomain(TherapistEntity entity) {
        return new Therapist(
                entity.getId(),
                new Account(
                        entity.getAccount().getId(),
                        entity.getAccount().getEmail(),
                        entity.getAccount().getPasswordHash(),
                        entity.getAccount().isEnabled(),
                        entity.getAccount().getCreatedAt(),
                        entity.getAccount().getUpdatedAt(),
                        Set.of(Role.THERAPIST)
                ),entity.getLicenseNumber(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.isActive()
        );
    }
}

