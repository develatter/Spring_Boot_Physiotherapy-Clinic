package com.develatter.fisioclinic.infraestructure.controller;

import com.develatter.fisioclinic.application.service.TherapistService;
import com.develatter.fisioclinic.infraestructure.controller.dto.response.TherapistResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/therapist")
public class TherapistController {
    private final TherapistService therapistService;

    public TherapistController(TherapistService therapistService) {
        this.therapistService = therapistService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<TherapistResponse> getPatientById(@PathVariable UUID id) {
        return ResponseEntity.ok(therapistService.getTherapistById(id));
    }

    @GetMapping("/account-id/{accountId}")
    public ResponseEntity<TherapistResponse> getPatientByAccountId(@PathVariable UUID accountId) {
        return ResponseEntity.ok(therapistService.getTherapistByAccountId(accountId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TherapistResponse>> getAllPatients() {
        return ResponseEntity.ok(therapistService.getAllTherapists());
    }

}
