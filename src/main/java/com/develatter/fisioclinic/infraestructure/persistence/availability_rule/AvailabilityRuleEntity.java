package com.develatter.fisioclinic.infraestructure.persistence.availability_rule;


import com.develatter.fisioclinic.infraestructure.persistence.therapist.TherapistEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "availability_rule")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class AvailabilityRuleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "therapist_id")
    private TherapistEntity therapist;

    @Min(1)
    @Max(7)
    @Column(name = "weekday", nullable = false)
    private int weekday;

    @Column(name = "start_time", nullable = false, columnDefinition = "time")
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false, columnDefinition = "time")
    private LocalTime endTime;

    @Size(max = 65)
    @Column(name = "timezone", nullable = false, length = 65)
    private String timezone = "Europe/Madrid";

    @Column(name = "valid_from", nullable = false)
    private LocalDate validFrom;

    @Column(name = "valid_to")
    private LocalDate validTo;

    @PrePersist
    @PreUpdate
    private void validateChecks() {
        if (endTime != null && startTime != null && !endTime.isAfter(startTime)) {
            throw new IllegalArgumentException("Invalid time range: end time must be after start time");
        }
        if (validTo != null && validFrom != null && !validTo.isAfter(validFrom)) {
            throw new IllegalArgumentException("Invalid date range: valid to must be after valid from");
        }
    }
}
