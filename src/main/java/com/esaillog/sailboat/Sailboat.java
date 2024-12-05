package com.esaillog.sailboat;

import com.esaillog.cruise.Cruise;
import com.esaillog.port.Port;
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
public class Sailboat {
    @Id
    private UUID id;
    private String name;
    private String registerNumber;
    private String type;
    @ManyToOne
    @JoinColumn(name = "port_id")
    private Port homePort;
    private double length;
    private double engineKW;
    @OneToMany(mappedBy = "sailboat")
    private Set<Cruise> cruises = new HashSet<>();

}
