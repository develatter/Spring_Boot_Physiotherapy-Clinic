package com.develatter.fisioclinic.application.port.out.read;

import com.develatter.fisioclinic.domain.model.Therapist;

import java.util.List;

public interface LoadAllTherapistsPort {
    List<Therapist> findAllTherapists();
}
