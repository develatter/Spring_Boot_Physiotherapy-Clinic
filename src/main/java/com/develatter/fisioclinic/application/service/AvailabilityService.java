package com.develatter.fisioclinic.application.service;

import com.develatter.fisioclinic.application.port.in.availability_rule.CreateAvailabilityRuleUseCase;
import com.develatter.fisioclinic.application.port.out.create.CreateAvailabilityRulePort;
import com.develatter.fisioclinic.application.port.out.read.LoadAvailabilityRulePort;
import com.develatter.fisioclinic.domain.model.AvailabilityRule;
import com.develatter.fisioclinic.domain.model.Therapist;
import com.develatter.fisioclinic.infraestructure.controller.dto.request.AvailabilityRuleRequest;
import com.develatter.fisioclinic.infraestructure.controller.dto.response.AvailabilityRuleResponse;
import com.develatter.fisioclinic.infraestructure.controller.dto.response.RoleResponse;
import com.develatter.fisioclinic.infraestructure.controller.dto.response.ServiceResponse;
import com.develatter.fisioclinic.infraestructure.controller.dto.response.TherapistResponse;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AvailabilityService implements CreateAvailabilityRuleUseCase {
    private final CreateAvailabilityRulePort createAvailabilityRulePort;
    private final LoadAvailabilityRulePort loadAvailabilityRulePort;

    public AvailabilityService(CreateAvailabilityRulePort createAvailabilityRulePort, LoadAvailabilityRulePort loadAvailabilityRulePort) {
        this.createAvailabilityRulePort = createAvailabilityRulePort;
        this.loadAvailabilityRulePort = loadAvailabilityRulePort;
    }


    @Override
    public AvailabilityRuleResponse createAvailabilityRule(AvailabilityRuleRequest request) {
        var rule = new AvailabilityRule(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
//TODO: map request to rule, including loading the therapist by id
        return toResponse(createAvailabilityRulePort.save(rule));
    }

    private AvailabilityRuleResponse toResponse(AvailabilityRule rule) {
        return new AvailabilityRuleResponse(
                rule.weekDay(),
                new TherapistResponse(
                        rule.therapist().licenseNumber(),
                        rule.therapist().firstName(),
                        rule.therapist().lastName(),
                        rule.therapist().account().email(),
                        rule.therapist().account().createdAt(),
                        rule.therapist().active(),
                        rule.therapist().account().enabled(),
                        rule.therapist().account().roles().stream().map(
                                role -> new RoleResponse(
                                        role.toString(),
                                        role.getRole()
                                )
                        ).collect(Collectors.toSet()),
                        rule.therapist().services().stream().map(
                                service -> new ServiceResponse(
                                        service.name(),
                                        service.durationInMinutes(),
                                        service.bufferTimeBeforeInMinutes(),
                                        service.bufferTimeAfterInMinutes(),
                                        service.priceInCents(),
                                        service.description(),
                                        service.active()
                                )
                        ).collect(Collectors.toSet())
                ),
                rule.startTime(),
                rule.endTime(),
                rule.timeZone(),
                rule.validFrom(),
                rule.validTo()

        );
    }


}
