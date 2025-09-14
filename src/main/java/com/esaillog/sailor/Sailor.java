package com.esaillog.sailor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.esaillog.cruise.Cruise;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Sailor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    @ManyToMany(mappedBy = "participants")
    private Set<Cruise> cruises = new HashSet<>();
    @OneToMany(mappedBy = "skipper")
    private Set<Cruise> skipperedCruises = new HashSet<>();

    @Override
    public String toString() {
        String cruiseNames = (cruises != null && Hibernate.isInitialized(cruises))
                ? cruises.stream()
                .map(Cruise::getName)
                .collect(Collectors.joining(", "))
                : "[uninitialized]";

        String skipperedCruiseNames = (skipperedCruises != null && Hibernate.isInitialized(skipperedCruises))
                ? skipperedCruises.stream()
                .map(Cruise::getName)
                .collect(Collectors.joining(", "))
                : "[uninitialized]";

        return "Sailor{" + "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", cruises=" + cruiseNames +
                ", skipperedCruises=" + skipperedCruiseNames +
                '}';
    }
}
