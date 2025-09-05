package com.esaillog.sailor;

import java.util.Set;
import java.util.UUID;

import com.esaillog.cruise.Cruise;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Sailor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    @ManyToMany(mappedBy = "participants")
    private Set<Cruise> cruises;
}
