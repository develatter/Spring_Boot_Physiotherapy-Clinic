package com.develatter.fisioclinic.infraestructure.persistence.role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "role")
@Data
public class RoleEntity {
    @Id
    private String code;

    @Column(nullable = false)
    private String description;
}
