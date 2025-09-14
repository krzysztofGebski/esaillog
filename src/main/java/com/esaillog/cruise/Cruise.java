package com.esaillog.cruise;

import com.esaillog.port.Port;
import com.esaillog.sailboat.Sailboat;
import com.esaillog.sailor.Sailor;
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
public class Cruise {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @ManyToMany
    @JoinTable(
            name = "cruise_sailor",
            joinColumns = @JoinColumn(name = "cruise_id"),
            inverseJoinColumns = @JoinColumn(name = "sailor_id")
    )
    private Set<Sailor> participants = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "cruise_port",
            joinColumns = @JoinColumn(name = "cruise_id"),
            inverseJoinColumns = @JoinColumn(name = "port_id")
    )
    private Set<Port> visitedPorts = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "sailboat_id")
    private Sailboat sailboat;
    @ManyToOne
    @JoinColumn(name = "skipper_id")
    private Sailor skipper;

    @Override
    public String toString() {
        String sailboatName = (sailboat != null && Hibernate.isInitialized(sailboat))
                ? sailboat.getName()
                : "null or uninitialized";

        String participantNames = (participants != null && Hibernate.isInitialized(participants))
                ? participants.stream()
                .map(s -> s.getFirstName() + " " + s.getLastName())
                .collect(Collectors.joining(", "))
                : "[lazy or uninitialized]";

        String visitedPortNames = (visitedPorts != null && Hibernate.isInitialized(visitedPorts))
                ? visitedPorts.stream()
                .map(Port::getName)
                .collect(Collectors.joining(", "))
                : "[lazy or uninitialized]";
        
        String skipperName = (skipper != null && Hibernate.isInitialized(skipper))
                ? skipper.getFirstName() + " " + skipper.getLastName()
                : "null or uninitialized";

        return "Cruise{id=" + id + ", name='" + name + '\'' + ", participants=[" + participantNames + "]" +
                ", visitedPorts=[" + visitedPortNames + "]" + ", sailboat=" + sailboatName +
                ", skipper=" + skipperName +
                '}';
    }
}
