package com.esaillog.training;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import com.esaillog.sailor.Sailor;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Training {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    @Enumerated(EnumType.STRING)
    private TrainingType type;
    private boolean hasCertificate;
    private LocalDate startDate;
    private LocalDate endDate;
    @ManyToMany(mappedBy = "trainings", cascade = { CascadeType.REMOVE})
    private Set<Sailor> sailors;
}
