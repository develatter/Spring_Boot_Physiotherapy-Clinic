package com.develatter.fisioclinic.infraestructure.controller;

import com.develatter.fisioclinic.application.service.AccountService;
import com.develatter.fisioclinic.infraestructure.controller.dto.CreateAccountRequest;
import com.develatter.fisioclinic.infraestructure.controller.dto.CreateAccountResponse;
import com.develatter.fisioclinic.infraestructure.controller.dto.CreateAdminAccountRequest;
import com.develatter.fisioclinic.infraestructure.controller.dto.CreatePatientAccountRequest;
import com.develatter.fisioclinic.infraestructure.controller.dto.CreateTherapistAccountRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/accounts")
@RestController
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @PostMapping("/create-admin")
    public ResponseEntity<CreateAccountResponse> createAdminAccount(
            @Valid @RequestBody CreateAdminAccountRequest request
    ) {
        CreateAccountResponse response = accountService.createAdminAccount(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create-patient")
    public ResponseEntity<CreateAccountResponse> createPatientAccount(
            @Valid @RequestBody CreatePatientAccountRequest request
    ) {
        CreateAccountResponse response = accountService.createPatientAccount(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create-therapist")
    public ResponseEntity<CreateAccountResponse> createTherapistAccount(
            @Valid @RequestBody CreateTherapistAccountRequest request
    ) {
        CreateAccountResponse response = accountService.createTherapistAccount(request);
        return ResponseEntity.ok(response);
    }
}
