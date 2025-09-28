package com.develatter.fisioclinic.application.port.in.service;

import com.develatter.fisioclinic.infraestructure.controller.dto.response.ServiceResponse;

import java.util.UUID;

public interface GetServiceUseCase {
    ServiceResponse getServiceById(UUID seriveId);
}
