package com.esaillog.sailor;

import java.util.Set;
import java.util.UUID;

import com.esaillog.training.Training;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Sailor {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    @ManyToMany
    @JoinTable(
        name = "sailor_training",
        joinColumns = @JoinColumn(name = "sailor_id"),
        inverseJoinColumns = @JoinColumn(name = "training_id")
    )
    private Set<Training> trainings;
}
