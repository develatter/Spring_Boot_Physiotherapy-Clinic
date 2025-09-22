package com.develatter.fisioclinic.infraestructure.persistence.services;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "service")
public class ServiceEntity {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column( nullable = false, length = 255)
    private String name;

    @Column(name = "duration_min", nullable = false)
    private int durationInMinutes;

    @Column(name = "buffer_time_before_min", nullable = false)
    private int bufferTimeBeforeInMinutes;

    @Column(name = "buffer_time_after_min", nullable = false)
    private int bufferTimeAfterInMinutes;

    @Column(name = "price_cents", nullable = false)
    private int priceInCents;

    @Column(columnDefinition = "text", nullable = false)
    private String description;

    @Column( nullable = false)
    private boolean active;
}