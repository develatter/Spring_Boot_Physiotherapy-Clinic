package com.develatter.fisioclinic.application.port.out.create;

import com.develatter.fisioclinic.domain.model.Service;

public interface CreateServicePort {
    Service save(Service service);
}
