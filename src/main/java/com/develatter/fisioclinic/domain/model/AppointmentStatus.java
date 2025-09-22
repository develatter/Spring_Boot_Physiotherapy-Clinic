package com.develatter.fisioclinic.domain.model;

public enum AppointmentStatus {
    SCHEDULED("Agendado"),
    COMPLETED("Completado"),
    CANCELED("Cancelado"),
    NO_SHOW("No asistió");

    private final String status;

    AppointmentStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
