package com.develatter.fisioclinic.application.port.out.create;

import com.develatter.fisioclinic.domain.model.AvailabilityRule;

public interface CreateAvailabilityRulePort {
    AvailabilityRule save(AvailabilityRule availabilityRule);
}
