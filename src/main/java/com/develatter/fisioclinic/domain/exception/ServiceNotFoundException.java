package com.develatter.fisioclinic.domain.exception;

public class ServiceNotFoundException extends RuntimeException {
    public ServiceNotFoundException(String serviceId) {
        super(String.format("Service not found with id: %s", serviceId));
    }
}
