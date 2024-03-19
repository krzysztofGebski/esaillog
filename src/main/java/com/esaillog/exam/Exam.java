package com.esaillog.exam;

import com.esaillog.sailor.Sailor;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Exam {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String name;
    private String number;
    private LocalDate date;
    private ExamType type;
    @ManyToMany(mappedBy = "exams", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    Set<Sailor> participants = new HashSet<>();
}
