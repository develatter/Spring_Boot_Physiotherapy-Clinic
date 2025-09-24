package com.develatter.fisioclinic.infraestructure.controller;

import com.develatter.fisioclinic.application.service.PatientService;
import com.develatter.fisioclinic.infraestructure.controller.dto.request.CreatePatientAccountRequest;
import com.develatter.fisioclinic.infraestructure.controller.dto.response.PatientResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable UUID id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @GetMapping("/account-id/{accountId}")
    public ResponseEntity<PatientResponse> getPatientByAccountId(@PathVariable UUID accountId) {
        return ResponseEntity.ok(patientService.getPatientByAccountId(accountId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PatientResponse>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }
}
