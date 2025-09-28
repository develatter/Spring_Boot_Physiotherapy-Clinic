package com.develatter.fisioclinic.infraestructure.persistence.patient;

import com.develatter.fisioclinic.application.port.out.create.CreatePatientPort;
import com.develatter.fisioclinic.application.port.out.read.LoadAllPatientsPort;
import com.develatter.fisioclinic.application.port.out.read.LoadPatientPort;
import com.develatter.fisioclinic.domain.model.Patient;
import com.develatter.fisioclinic.domain.model.Role;
import com.develatter.fisioclinic.infraestructure.persistence.account.AccountEntity;
import com.develatter.fisioclinic.domain.model.Account;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public class PatientPersistenceAdapter implements CreatePatientPort, LoadPatientPort, LoadAllPatientsPort {
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

    @Override
    public List<Patient> findAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public boolean existsById(UUID patientId) {
        return patientRepository
                .findById(patientId)
                .isPresent();
    }

    @Override
    public Optional<Patient> findById(UUID patientId) {
        return patientRepository.findById(patientId).map(this::toDomain);
    }

    @Override
    public Optional<Patient> findByAccountId(UUID accountId) {
        return patientRepository.findByAccountId(accountId).map(this::toDomain);
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
                new Account(
                        entity.getAccount().getId(),
                        entity.getAccount().getEmail(),
                        entity.getAccount().getPasswordHash(),
                        entity.getAccount().isEnabled(),
                        entity.getAccount().getCreatedAt(),
                        entity.getAccount().getUpdatedAt(),
                        Set.of(Role.PATIENT)
                ),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getBirthDate(),
                entity.getPhoneNumber(),
                entity.getAddress()
        );
    }
}
