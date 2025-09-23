package com.develatter.fisioclinic.infraestructure.persistence.therapist;

import com.develatter.fisioclinic.application.port.out.create.CreateTherapistPort;
import com.develatter.fisioclinic.domain.model.Role;
import com.develatter.fisioclinic.domain.model.Therapist;
import com.develatter.fisioclinic.infraestructure.persistence.account.AccountEntity;
import com.develatter.fisioclinic.domain.model.Account;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class TherapistPersistenceAdapter implements CreateTherapistPort {
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

