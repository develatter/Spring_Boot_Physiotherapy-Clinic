package com.develatter.fisioclinic.application.port.in.availability_rule;

import com.develatter.fisioclinic.infraestructure.controller.dto.request.AvailabilityRuleRequest;
import com.develatter.fisioclinic.infraestructure.controller.dto.response.AvailabilityRuleResponse;

public interface CreateAvailabilityRuleUseCase {
    AvailabilityRuleResponse createAvailabilityRule(AvailabilityRuleRequest request);
}
