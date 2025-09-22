package com.develatter.fisioclinic.infraestructure.persistence.role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "role")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class RoleEntity {
    @Id
    @Column(length = 32)
    private String code;

    @Column(nullable = false)
    private String description;
}
