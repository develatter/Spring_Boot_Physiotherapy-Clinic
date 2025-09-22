package com.develatter.fisioclinic.domain.model;

public enum Role {
    PATIENT("Paciente"),
    THERAPIST("Terapeuta"),
    ADMIN("Administrador");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}