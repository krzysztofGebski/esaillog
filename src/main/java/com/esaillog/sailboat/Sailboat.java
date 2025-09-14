package com.esaillog.sailboat;

import com.esaillog.cruise.Cruise;
import com.esaillog.port.Port;
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
public class Sailboat {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String registerNumber;
    @Enumerated(EnumType.STRING)
    private SailboatType type;
    @ManyToOne
    @JoinColumn(name = "port_id")
    private Port homePort;
    private double length;
    private double engineKW;
    @OneToMany(mappedBy = "sailboat")
    private Set<Cruise> cruises = new HashSet<>();

    @Override
    public String toString() {
        String homePortName = (homePort != null && Hibernate.isInitialized(homePort))
                ? homePort.getName()
                : "null or uninitialized";

        String cruiseNames = (cruises != null && Hibernate.isInitialized(cruises))
                ? cruises.stream()
                .map(Cruise::getName)
                .collect(Collectors.joining(", "))
                : "[lazy or uninitialized]";

        return "Sailboat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", registerNumber='" + registerNumber + '\'' +
                ", type='" + type + '\'' +
                ", homePort=" + homePortName +
                ", length=" + length +
                ", engineKW=" + engineKW +
                ", cruises=[" + cruiseNames + "]" +
                '}';
    }
}
