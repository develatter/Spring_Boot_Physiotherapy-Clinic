package com.develatter.fisioclinic.infraestructure.persistence.services;

import com.develatter.fisioclinic.application.port.out.create.CreateServicePort;
import com.develatter.fisioclinic.application.port.out.read.LoadAllServicesPort;
import com.develatter.fisioclinic.application.port.out.read.LoadServicePort;
import com.develatter.fisioclinic.domain.model.Service;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ServicePersistenceAdapter implements CreateServicePort, LoadServicePort, LoadAllServicesPort {

    private final SpringDataServiceRepository serviceRepository;

    public ServicePersistenceAdapter(SpringDataServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }


    @Override
    public Service save(Service service) {
        return toDomain(
                serviceRepository.save(
                        toEntity(service)
                )
        ) ;
    }

    @Override
    public List<Service> findAllServices() {
        return serviceRepository
                .findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<Service> findById(UUID serviceId) {
        return serviceRepository
                .findById(serviceId)
                .map(this::toDomain);
    }

    private Service toDomain(ServiceEntity entity) {
        return new Service(
                entity.getId(),
                entity.getName(),
                entity.getDurationInMinutes(),
                entity.getBufferTimeBeforeInMinutes(),
                entity.getBufferTimeAfterInMinutes(),
                entity.getPriceInCents(),
                entity.getDescription(),
                entity.isActive()
        );
    }

    private ServiceEntity toEntity(Service service) {
        return ServiceEntity.builder()
                .id(service.id())
                .name(service.name())
                .durationInMinutes(service.durationInMinutes())
                .bufferTimeBeforeInMinutes(service.bufferTimeBeforeInMinutes())
                .bufferTimeAfterInMinutes(service.bufferTimeAfterInMinutes())
                .priceInCents(service.priceInCents())
                .description(service.description())
                .active(service.active())
                .build();
    }
}