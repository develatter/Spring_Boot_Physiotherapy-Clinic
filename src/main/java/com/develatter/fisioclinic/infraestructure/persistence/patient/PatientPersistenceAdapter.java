package com.develatter.fisioclinic.infraestructure.persistence.patient;

import com.develatter.fisioclinic.application.port.out.CreatePatientPort;
import com.develatter.fisioclinic.domain.model.Patient;
import com.develatter.fisioclinic.infraestructure.persistence.account.AccountEntity;
import com.develatter.fisioclinic.domain.model.Account;
import org.springframework.stereotype.Component;

@Component
public class PatientPersistenceAdapter implements CreatePatientPort {
    private final SpringDataPatientRepository patientRepository;

    public PatientPersistenceAdapter(SpringDataPatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Patient save(Patient patient) {
        PatientEntity entity = toEntity(patient);
        PatientEntity saved = patientRepository.save(entity);
        return toDomain(saved);
    }

    private PatientEntity toEntity(Patient patient) {
        return PatientEntity.builder()
                .id(patient.id())
                .account(AccountEntity.builder().id(patient.account().id()).build())
                .firstName(patient.firstName())
                .lastName(patient.lastName())
                .birthDate(patient.birthDate())
                .phoneNumber(patient.phoneNumber())
                .address(patient.address())
                .build();
    }

    private Patient toDomain(PatientEntity entity) {
        return new Patient(
                entity.getId(),
                new Account(entity.getAccount().getId(), null, null, false, null, null, null),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getBirthDate(),
                entity.getPhoneNumber(),
                entity.getAddress()
        );
    }
}

