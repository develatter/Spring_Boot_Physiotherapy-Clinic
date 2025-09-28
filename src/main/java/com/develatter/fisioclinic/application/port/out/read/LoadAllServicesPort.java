package com.develatter.fisioclinic.application.port.out.read;

import com.develatter.fisioclinic.domain.model.Service;

import java.util.List;

public interface LoadAllServicesPort {
    List<Service> findAllServices();
}
