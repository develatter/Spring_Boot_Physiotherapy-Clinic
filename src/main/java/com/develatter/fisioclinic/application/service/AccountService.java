package com.develatter.fisioclinic.application.service;

import com.develatter.fisioclinic.application.port.in.account.CreateAdminAccountUseCase;
import com.develatter.fisioclinic.application.port.in.account.CreatePatientAccountUseCase;
import com.develatter.fisioclinic.application.port.in.account.CreateTherapistAccountUseCase;
import com.develatter.fisioclinic.application.port.out.create.CreateAccountPort;
import com.develatter.fisioclinic.application.port.out.create.CreatePatientPort;
import com.develatter.fisioclinic.application.port.out.create.CreateTherapistPort;
import com.develatter.fisioclinic.domain.model.Account;
import com.develatter.fisioclinic.domain.model.Patient;
import com.develatter.fisioclinic.domain.model.Role;
import com.develatter.fisioclinic.domain.model.Therapist;
import com.develatter.fisioclinic.infraestructure.controller.dto.response.AccountResponse;
import com.develatter.fisioclinic.infraestructure.controller.dto.request.AdminAccountRequest;
import com.develatter.fisioclinic.infraestructure.controller.dto.request.PatientAccountRequest;
import com.develatter.fisioclinic.infraestructure.controller.dto.request.TherapistAccountRequest;
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
    public AccountResponse createAdminAccount(AdminAccountRequest request) {
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
        return new AccountResponse(
                saved.id(),
                saved.email(),
                saved.enabled(),
                saved.roles().stream().map(Role::name).collect(Collectors.toSet()),
                saved.createdAt()
        );
    }

    @Transactional
    @Override
    public AccountResponse createPatientAccount(PatientAccountRequest request) {
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
        return new AccountResponse(
                savedAccount.id(),
                savedAccount.email(),
                savedAccount.enabled(),
                savedAccount.roles().stream().map(Role::name).collect(Collectors.toSet()),
                savedAccount.createdAt()
        );
    }

    @Transactional
    @Override
    public AccountResponse createTherapistAccount(TherapistAccountRequest request) {
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
        var services =
                request.services().stream()
                .map(
                        s -> new com.develatter.fisioclinic.domain.model.Service(
                                null,
                                s.name(),
                                s.durationInMinutes(),
                                s.bufferTimeBeforeInMinutes(),
                                s.bufferTimeAfterInMinutes(),
                                s.priceInCents(),
                                s.description(),
                                s.active()
                        )
                )
                .collect(Collectors.toSet());
        Therapist therapist = new Therapist(
                null,
                savedAccount,
                request.licenseNumber(),
                request.firstName(),
                request.lastName(),
                true,
                services
        );

        createTherapistPort.save(therapist);
        return new AccountResponse(
                savedAccount.id(),
                savedAccount.email(),
                savedAccount.enabled(),
                savedAccount.roles().stream().map(Role::name).collect(Collectors.toSet()),
                savedAccount.createdAt()
        );
    }
}
