package com.develatter.fisioclinic.application.service;

import com.develatter.fisioclinic.application.port.in.CreateAdminAccountUseCase;
import com.develatter.fisioclinic.application.port.in.CreatePatientAccountUseCase;
import com.develatter.fisioclinic.application.port.in.CreateTherapistAccountUseCase;
import com.develatter.fisioclinic.application.port.out.CreateAccountPort;
import com.develatter.fisioclinic.application.port.out.CreatePatientPort;
import com.develatter.fisioclinic.application.port.out.CreateTherapistPort;
import com.develatter.fisioclinic.domain.model.Account;
import com.develatter.fisioclinic.domain.model.Patient;
import com.develatter.fisioclinic.domain.model.Role;
import com.develatter.fisioclinic.domain.model.Therapist;
import com.develatter.fisioclinic.infraestructure.controller.dto.CreateAccountResponse;
import com.develatter.fisioclinic.infraestructure.controller.dto.CreateAdminAccountRequest;
import com.develatter.fisioclinic.infraestructure.controller.dto.CreatePatientAccountRequest;
import com.develatter.fisioclinic.infraestructure.controller.dto.CreateTherapistAccountRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountService implements  CreateAdminAccountUseCase, CreatePatientAccountUseCase, CreateTherapistAccountUseCase {
    private final CreateAccountPort createAccountPort;
    private final CreatePatientPort createPatientPort;
    private final CreateTherapistPort createTherapistPort;
    private final PasswordEncoder passwordEncoder;

    public AccountService(CreateAccountPort createAccountPort, CreatePatientPort createPatientPort, CreateTherapistPort createTherapistPort, PasswordEncoder passwordEncoder) {
        this.createAccountPort = createAccountPort;
        this.createPatientPort = createPatientPort;
        this.createTherapistPort = createTherapistPort;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public CreateAccountResponse createAdminAccount(CreateAdminAccountRequest request) {
        Account account = new Account(
                null,
                request.email(),
                passwordEncoder.encode(request.password()),
                true,
                null,
                null,
                Set.of(Role.ADMIN)
        );
        Account saved = createAccountPort.save(account);
        return new CreateAccountResponse(
                saved.id(),
                saved.email(),
                saved.enabled(),
                saved.roles().stream().map(Role::name).collect(Collectors.toSet()),
                saved.createdAt()
        );
    }

    @Transactional
    @Override
    public CreateAccountResponse createPatientAccount(CreatePatientAccountRequest request) {
        Account account = new Account(
                null,
                request.email(),
                passwordEncoder.encode(request.password()),
                true,
                null,
                null,
                Set.of(Role.PATIENT)
        );
        Account savedAccount = createAccountPort.save(account);
        Patient patient = new Patient(
                null,
                savedAccount,
                request.firstName(),
                request.lastName(),
                request.birthDate(),
                request.phoneNumber(),
                request.address()
        );
        createPatientPort.save(patient);
        return new CreateAccountResponse(
                savedAccount.id(),
                savedAccount.email(),
                savedAccount.enabled(),
                savedAccount.roles().stream().map(Role::name).collect(Collectors.toSet()),
                savedAccount.createdAt()
        );
    }

    @Transactional
    @Override
    public CreateAccountResponse createTherapistAccount(CreateTherapistAccountRequest request) {
        Account account = new Account(
                null,
                request.email(),
                passwordEncoder.encode(request.password()),
                true,
                null,
                null,
                Set.of(Role.THERAPIST)
        );
        Account savedAccount = createAccountPort.save(account);
        Therapist therapist = new Therapist(
                null,
                savedAccount,
                request.licenseNumber(),
                request.firstName(),
                request.lastName(),
                true
        );
        createTherapistPort.save(therapist);
        return new CreateAccountResponse(
                savedAccount.id(),
                savedAccount.email(),
                savedAccount.enabled(),
                savedAccount.roles().stream().map(Role::name).collect(Collectors.toSet()),
                savedAccount.createdAt()
        );
    }
}
