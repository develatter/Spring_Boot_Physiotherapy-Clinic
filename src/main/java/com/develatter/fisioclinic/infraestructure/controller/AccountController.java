package com.develatter.fisioclinic.infraestructure.controller;

import com.develatter.fisioclinic.application.service.AccountService;
import com.develatter.fisioclinic.infraestructure.controller.dto.response.AccountResponse;
import com.develatter.fisioclinic.infraestructure.controller.dto.request.AdminAccountRequest;
import com.develatter.fisioclinic.infraestructure.controller.dto.request.PatientAccountRequest;
import com.develatter.fisioclinic.infraestructure.controller.dto.request.TherapistAccountRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/accounts")
@RestController
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @PostMapping("/create-admin")
    public ResponseEntity<AccountResponse> createAdminAccount(
            @Valid @RequestBody AdminAccountRequest request
    ) {
        AccountResponse response = accountService.createAdminAccount(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create-patient")
    public ResponseEntity<AccountResponse> createPatientAccount(
            @Valid @RequestBody PatientAccountRequest request
    ) {
        AccountResponse response = accountService.createPatientAccount(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create-therapist")
    public ResponseEntity<AccountResponse> createTherapistAccount(
            @Valid @RequestBody TherapistAccountRequest request
    ) {
        AccountResponse response = accountService.createTherapistAccount(request);
        return ResponseEntity.ok(response);
    }

}
