package com.develatter.fisioclinic.infraestructure.persistence.appointment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "appointment_status")
public class AppointmentStatusEntity {
    @Id @Column(length = 32)
    private String code;

    @Column(nullable = false, columnDefinition = "text")
    private String description;
}
