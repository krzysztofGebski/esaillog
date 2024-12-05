package com.esaillog.port;

import com.esaillog.cruise.Cruise;
import com.esaillog.sailboat.Sailboat;
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
public class Port {
    @Id
    private UUID id;
    private String name;
    private String description;
    @OneToMany(mappedBy = "homePort")
    private Set<Sailboat> sailboats = new HashSet<>();
    @ManyToMany(mappedBy = "visitedPorts")
    private Set<Cruise> cruises = new HashSet<>();
}
