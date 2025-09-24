package com.develatter.fisioclinic.application.port.in.therapist;

import com.develatter.fisioclinic.infraestructure.controller.dto.response.TherapistResponse;

import java.util.List;

public interface ListTherapistsUseCase {
    List<TherapistResponse> getAllTherapists();
}
