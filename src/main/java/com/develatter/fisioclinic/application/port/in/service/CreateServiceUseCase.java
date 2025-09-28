package com.develatter.fisioclinic.application.port.in.service;

import com.develatter.fisioclinic.infraestructure.controller.dto.request.ServiceRequest;
import com.develatter.fisioclinic.infraestructure.controller.dto.response.ServiceResponse;

public interface CreateServiceUseCase {
    ServiceResponse createService(ServiceRequest request);
}
