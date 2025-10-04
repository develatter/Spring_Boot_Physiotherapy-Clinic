package com.develatter.fisioclinic.application.service;

import com.develatter.fisioclinic.application.port.in.therapist.GetTherapistUseCase;
import com.develatter.fisioclinic.application.port.in.therapist.ListTherapistsUseCase;
import com.develatter.fisioclinic.application.port.out.read.LoadAllTherapistsPort;
import com.develatter.fisioclinic.application.port.out.read.LoadTherapistPort;
import com.develatter.fisioclinic.domain.exception.ErrorType;
import com.develatter.fisioclinic.domain.exception.TherapistNotFoundException;
import com.develatter.fisioclinic.domain.model.Role;
import com.develatter.fisioclinic.domain.model.Therapist;
import com.develatter.fisioclinic.infraestructure.controller.dto.response.TherapistResponse;
import com.develatter.fisioclinic.infraestructure.controller.dto.response.RoleResponse;
import com.develatter.fisioclinic.infraestructure.controller.dto.response.ServiceResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TherapistService implements GetTherapistUseCase, ListTherapistsUseCase {

    private final LoadTherapistPort loadTherapistPort;
    private final LoadAllTherapistsPort loadAllTherapistsPort;

    public TherapistService(LoadTherapistPort loadTherapistPort, LoadAllTherapistsPort loadAllTherapistsPort) {
        this.loadTherapistPort = loadTherapistPort;
        this.loadAllTherapistsPort = loadAllTherapistsPort;
    }

    @Override
    public TherapistResponse getTherapistById(UUID therapistid) {

        return loadTherapistPort.findById(therapistid)
                .map(this::toResponse)
                .orElseThrow(() -> new TherapistNotFoundException(ErrorType.ID, therapistid.toString()));
    }

    @Override
    public TherapistResponse getTherapistByAccountId(UUID accountId) {
        return loadTherapistPort.findByAccountId(accountId)
                .map(this::toResponse)
                .orElseThrow(() -> new TherapistNotFoundException(ErrorType.ACCOUNT_ID, accountId.toString()));
    }

    @Override
    public List<TherapistResponse> getAllTherapists() {
        var therapists = loadAllTherapistsPort.findAllTherapists();
        return (therapists == null || therapists.isEmpty()) ?
                List.of()
                :
                loadAllTherapistsPort
                        .findAllTherapists()
                        .stream()
                        .map(this::toResponse)
                        .toList();
    }

    private TherapistResponse toResponse(Therapist therapist) {
        return new TherapistResponse(
                therapist.licenseNumber(),
                therapist.firstName(),
                therapist.lastName(),
                therapist.account().email(),
                therapist.account().createdAt(),
                therapist.active(),
                therapist.account().enabled(),
                mapRolesToRoleResponses(therapist.account().roles()),
                mapServicesToServiceResponses(therapist.services())
        );
    }

    private Set<RoleResponse> mapRolesToRoleResponses(Set<Role> roles) {
        if (roles == null) return Set.of();
        return roles.stream()
                .map(role -> new RoleResponse(role.toString(), role.name())) // Personaliza la descripción si lo deseas
                .collect(Collectors.toSet());
    }

    private Set<ServiceResponse> mapServicesToServiceResponses(Set<com.develatter.fisioclinic.domain.model.Service> services) {
        if (services == null) return Set.of();
        return services.stream()
                .map(s -> new ServiceResponse(
                        s.name(),
                        s.durationInMinutes(),
                        s.bufferTimeBeforeInMinutes(),
                        s.bufferTimeAfterInMinutes(),
                        s.priceInCents(),
                        s.description(),
                        s.active()
                ))
                .collect(Collectors.toSet());
    }
}
