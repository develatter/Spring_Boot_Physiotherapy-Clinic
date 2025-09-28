package com.develatter.fisioclinic.application.service;

import com.develatter.fisioclinic.application.port.in.service.CreateServiceUseCase;
import com.develatter.fisioclinic.application.port.in.service.GetServiceUseCase;
import com.develatter.fisioclinic.application.port.in.service.ListServicesUseCase;
import com.develatter.fisioclinic.application.port.out.create.CreateServicePort;
import com.develatter.fisioclinic.application.port.out.read.LoadAllServicesPort;
import com.develatter.fisioclinic.application.port.out.read.LoadServicePort;
import com.develatter.fisioclinic.domain.exception.ServiceNotFoundException;
import com.develatter.fisioclinic.infraestructure.controller.dto.request.ServiceRequest;
import com.develatter.fisioclinic.infraestructure.controller.dto.response.ServiceResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ServiceService implements CreateServiceUseCase, GetServiceUseCase, ListServicesUseCase {

    private final LoadServicePort loadServicePort;
    private final LoadAllServicesPort loadAllServicesPort;
    private final CreateServicePort createServicePort;


    public ServiceService(
            LoadServicePort loadServicePort,
            LoadAllServicesPort loadAllServicesPort,
            CreateServicePort createServicePort
    ) {
        this.loadServicePort = loadServicePort;
        this.loadAllServicesPort = loadAllServicesPort;
        this.createServicePort = createServicePort;
    }

    @Override
    public ServiceResponse createService(ServiceRequest request) {
        var service = new com.develatter.fisioclinic.domain.model.Service(
                null,
                request.name(),
                request.durationInMinutes(),
                request.bufferTimeBeforeInMinutes(),
                request.bufferTimeAfterInMinutes(),
                request.priceInCents(),
                request.description(),
                request.active()
        );
        var savedService = createServicePort.save(service);
        return toResponse(savedService);
    }

    @Override
    public ServiceResponse getServiceById(UUID serviceId) {
        return loadServicePort.findById(serviceId)
                .map(this::toResponse)
                .orElseThrow(() -> new ServiceNotFoundException(serviceId.toString()));
    }

    @Override
    public List<ServiceResponse> getAllServices() {
        return loadAllServicesPort.findAllServices()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private ServiceResponse toResponse(com.develatter.fisioclinic.domain.model.Service service) {
        return new ServiceResponse(
                service.name(),
                service.durationInMinutes(),
                service.bufferTimeBeforeInMinutes(),
                service.bufferTimeAfterInMinutes(),
                service.priceInCents(),
                service.description(),
                service.active()
        );
    }
}
