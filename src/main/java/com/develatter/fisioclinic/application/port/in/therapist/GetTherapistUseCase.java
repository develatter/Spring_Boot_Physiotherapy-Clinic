package com.develatter.fisioclinic.application.port.in.therapist;

import com.develatter.fisioclinic.infraestructure.controller.dto.response.TherapistResponse;

import java.util.UUID;

public interface GetTherapistUseCase {
    TherapistResponse getTherapistById(UUID patientId);
    TherapistResponse getTherapistByAccountId(UUID accountId);
}
