package com.develatter.fisioclinic.infraestructure.persistence.availability_rule;

import com.develatter.fisioclinic.application.port.out.create.CreateAvailabilityRulePort;
import com.develatter.fisioclinic.application.port.out.read.LoadAvailabilityRulePort;
import com.develatter.fisioclinic.domain.model.Account;
import com.develatter.fisioclinic.domain.model.AvailabilityRule;
import com.develatter.fisioclinic.domain.model.Role;
import com.develatter.fisioclinic.domain.model.Therapist;
import com.develatter.fisioclinic.infraestructure.persistence.therapist.TherapistEntity;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;

@Repository
public class AvailabilityRulePersistenceAdapter implements LoadAvailabilityRulePort, CreateAvailabilityRulePort {


    private final SpringDataAvailabilityRuleRepository availabilityRuleRepository;

    public AvailabilityRulePersistenceAdapter(SpringDataAvailabilityRuleRepository availabilityRuleRepository) {
        this.availabilityRuleRepository = availabilityRuleRepository;
    }

    @Override
    public AvailabilityRule save(AvailabilityRule availabilityRule) {
        var savedEntity = availabilityRuleRepository.save(toEntity(availabilityRule));
        return toDomain(savedEntity);
    }

    @Override
    public Optional<AvailabilityRule> findAvailabilityRuleById(UUID id) {
        return availabilityRuleRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<AvailabilityRule> findByTherapistId(UUID therapistId) {
        return availabilityRuleRepository.findByTherapistId(therapistId).map(this::toDomain);
    }

    private AvailabilityRuleEntity toEntity(AvailabilityRule availabilityRule) {
        return AvailabilityRuleEntity
                .builder()
                .id(availabilityRule.id())
                .therapist(
                        TherapistEntity.builder()
                                .id(availabilityRule.therapist().id())
                                .build()
                )
                .weekday(availabilityRule.weekDay().getValue())
                .startTime(availabilityRule.startTime())
                .endTime(availabilityRule.endTime())
                .timezone(availabilityRule.timeZone().toString())
                .validTo(availabilityRule.validTo())
                .validFrom(availabilityRule.validFrom())
                .build();
    }

    private AvailabilityRule toDomain(AvailabilityRuleEntity availabilityRuleEntity) {
        return  new AvailabilityRule(
                availabilityRuleEntity.getId(),
                new Therapist(
                        availabilityRuleEntity.getTherapist().getId(),
                        new Account(availabilityRuleEntity.getTherapist().getAccount().getId(),
                                availabilityRuleEntity.getTherapist().getAccount().getEmail(),
                                availabilityRuleEntity.getTherapist().getAccount().getPasswordHash(),
                                availabilityRuleEntity.getTherapist().getAccount().isEnabled(),
                                availabilityRuleEntity.getTherapist().getAccount().getCreatedAt(),
                                availabilityRuleEntity.getTherapist().getAccount().getUpdatedAt(),
                                Set.of(Role.THERAPIST)
                        ),
                        availabilityRuleEntity.getTherapist().getLicenseNumber(),
                        availabilityRuleEntity.getTherapist().getFirstName(),
                        availabilityRuleEntity.getTherapist().getLastName(),
                        availabilityRuleEntity.getTherapist().isActive(),
                        Set.of()
                ),
                DayOfWeek.of(availabilityRuleEntity.getWeekday()),
                availabilityRuleEntity.getStartTime(),
                availabilityRuleEntity.getEndTime(),
                TimeZone.getTimeZone(availabilityRuleEntity.getTimezone()),
                availabilityRuleEntity.getValidFrom(),
                availabilityRuleEntity.getValidTo()
        );
    }
}
