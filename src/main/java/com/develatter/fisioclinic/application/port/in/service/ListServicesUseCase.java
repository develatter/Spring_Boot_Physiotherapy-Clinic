package com.develatter.fisioclinic.application.port.in.service;

import com.develatter.fisioclinic.infraestructure.controller.dto.response.ServiceResponse;

import java.util.List;

public interface ListServicesUseCase {
    List<ServiceResponse> getAllServices();
}
