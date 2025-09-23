package com.develatter.fisioclinic.application.port.out;

import com.develatter.fisioclinic.domain.model.Therapist;

public interface CreateTherapistPort {
    Therapist save(Therapist therapist);
}

