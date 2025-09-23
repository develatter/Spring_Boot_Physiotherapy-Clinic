package com.develatter.fisioclinic.application.port.out.read;

import com.develatter.fisioclinic.domain.model.Patient;

import java.util.List;

public interface LoadAllPatientsPort {
    List<Patient> findAllPatients();
}
