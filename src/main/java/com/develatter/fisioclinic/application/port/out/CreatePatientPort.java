package com.develatter.fisioclinic.application.port.out;

import com.develatter.fisioclinic.domain.model.Patient;

public interface CreatePatientPort {
    Patient save(Patient patient);
}

