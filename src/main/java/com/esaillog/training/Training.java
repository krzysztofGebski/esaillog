package com.esaillog.training;

import com.esaillog.sailor.Sailor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Training {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String name;
    @Enumerated(EnumType.STRING)
    private TrainingType type;
    private boolean hasCertificate;
    private LocalDate startDate;
    private LocalDate endDate;
    @ManyToMany(mappedBy = "trainings", cascade = {CascadeType.REMOVE})
    private List<Sailor> sailors;
}
