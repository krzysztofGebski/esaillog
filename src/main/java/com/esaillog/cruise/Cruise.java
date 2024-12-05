package com.esaillog.cruise;

import com.esaillog.port.Port;
import com.esaillog.sailboat.Sailboat;
import com.esaillog.sailor.Sailor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cruise {
    @Id
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
}

