package com.esaillog.port;

import com.esaillog.cruise.Cruise;
import com.esaillog.sailboat.Sailboat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Port {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;
    @OneToMany(mappedBy = "homePort")
    private Set<Sailboat> sailboats = new HashSet<>();
    @ManyToMany(mappedBy = "visitedPorts")
    private Set<Cruise> cruises = new HashSet<>();

    @Override
    public String toString() {
        String sailboatNames = (sailboats != null && Hibernate.isInitialized(sailboats))
                ? sailboats.stream()
                .map(Sailboat::getName)
                .collect(Collectors.joining(", "))
                : "[lazy or uninitialized]";

        String cruiseNames = (cruises != null && Hibernate.isInitialized(cruises))
                ? cruises.stream()
                .map(Cruise::getName)
                .collect(Collectors.joining(", "))
                : "[lazy or uninitialized]";

        return "Port{" + "id=" + id + ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sailboats=[" + sailboatNames + "]" +
                ", cruises=[" + cruiseNames + "]" + '}';
    }
}
