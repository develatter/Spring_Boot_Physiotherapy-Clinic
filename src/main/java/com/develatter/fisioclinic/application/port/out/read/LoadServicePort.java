package com.develatter.fisioclinic.application.port.out.read;

import com.develatter.fisioclinic.domain.model.Service;

import java.util.Optional;
import java.util.UUID;

public interface LoadServicePort {
    Optional<Service> findById(UUID serviceId);
}
