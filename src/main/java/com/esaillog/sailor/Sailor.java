package com.esaillog.sailor;

import com.esaillog.exam.Exam;
import com.esaillog.training.Training;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Sailor {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "sailor_training",
        joinColumns = @JoinColumn(name = "sailor_id"),
        inverseJoinColumns = @JoinColumn(name = "training_id")
    )
    private Set<Training> trainings = new HashSet<>();
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "sailor_exam",
            joinColumns = @JoinColumn(name = "sailor_id"),
            inverseJoinColumns = @JoinColumn(name = "exam_id")
    )
    private Set<Exam> exams = new HashSet<>();
}
